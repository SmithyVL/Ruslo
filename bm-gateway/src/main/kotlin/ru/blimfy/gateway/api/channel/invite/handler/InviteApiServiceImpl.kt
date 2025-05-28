package ru.blimfy.gateway.api.channel.invite.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.InviteTypes.GROUP_DM
import ru.blimfy.common.enumeration.InviteTypes.SERVER
import ru.blimfy.gateway.api.dto.InviteDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.dto.toPartialDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.integration.websockets.base.EntityDeleteDto
import ru.blimfy.server.db.entity.Member
import ru.blimfy.server.usecase.ban.BanService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.WsMessageTypes.INVITE_DELETE
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_ADD

/**
 * Реализация интерфейса для работы с обработкой запросов о приглашениях.
 *
 * @property inviteService сервис для работы с приглашениями.
 * @property channelService сервис для работы с каналами.
 * @property serverService сервис для работы с серверами.
 * @property memberService сервис для работы с участниками серверов.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @property banService сервис для работы с банами серверов.
 * @property userService сервис для работы с пользователями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class InviteApiServiceImpl(
    private val inviteService: InviteService,
    private val channelService: ChannelService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val roleService: RoleService,
    private val memberRoleService: MemberRoleService,
    private val banService: BanService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : InviteApiService {
    override suspend fun findInvite(id: UUID) =
        inviteService.findInvite(id).toDtoWithLinkData()

    override suspend fun deleteInvite(id: UUID, user: User): InviteDto {
        val invite = inviteService.findInvite(id)
        val serverId = invite.serverId

        // Удалить приглашение на сервер может только его создатель.
        serverService.checkServerWrite(serverId = serverId!!, userId = user.id)

        return inviteService.deleteInvite(id = id).toDtoWithLinkData()
            .apply {
                val data = EntityDeleteDto(id = id, channelId = invite.channelId, serverId = serverId)
                userWsStorage.sendMessage(INVITE_DELETE, data)
            }
    }

    override suspend fun useInvite(id: UUID, user: User) {
        inviteService.findInvite(id).let { invite ->
            when (invite.type) {
                SERVER ->
                    if (banService.findBan(serverId = invite.serverId!!, userId = user.id) == null) {
                        serverService.addNewMember(serverId = invite.serverId!!, userId = user.id)
                            .toDtoWithLinkData(user)
                            .apply { userWsStorage.sendMessage(SERVER_MEMBER_ADD, this) }
                    }

                GROUP_DM -> channelService.addRecipients(invite.channelId, setOf(user.id))
                        .toDtoWithLinkData()
                        .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }
            }
        }
    }

    /**
     * Возвращает DTO представление приглашения.
     */
    private suspend fun Invite.toDtoWithLinkData() = this.toDto().apply {
        channel = channelService.findChannel(channelId).toPartialDto()
        inviter = userService.findUser(authorId).toDto()

        serverId?.let { serverId ->
            server = serverService.findServer(serverId).toPartialDto(false)
            approximateMemberCount = memberService.getCountServerMembers(serverId)
        }
    }

    /**
     * Возвращает DTO представление группы.
     */
    private suspend fun Channel.toDtoWithLinkData() =
        this.toDto().apply {
            recipients = this@toDtoWithLinkData.recipients
                ?.map { recipientId -> userService.findUser(recipientId) }
                ?.map(User::toDto)
        }

    /**
     * Возвращает DTO представление участника сервера для [user].
     */
    private suspend fun Member.toDtoWithLinkData(user: User) =
        this.toDto().apply {
            this.user = user.toDto()
            roles = memberRoleService.findMemberRoles(id)
                .map { roleService.findRole(it.roleId) }
                .map { it.toDto() }
                .toList()
            userWsStorage.sendMessage(SERVER_MEMBER_ADD, this)
        }
}