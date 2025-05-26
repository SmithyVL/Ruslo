package ru.blimfy.gateway.service.channel.group

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.InviteTypes.GROUP_DM
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.invite.toDto
import ru.blimfy.gateway.dto.channel.toDto
import ru.blimfy.gateway.dto.channel.toPartialDto
import ru.blimfy.gateway.dto.server.toPartialDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.WsMessageTypes.INVITE_CREATE

/**
 * Реализация интерфейса для работы с обработкой запросов о группах.
 *
 * @property channelService сервис для работы с каналами.
 * @property inviteService сервис для работы с приглашениями.
 * @property serverService сервис для работы с серверами.
 * @property memberService сервис для работы с участниками серверов.
 * @property userService сервис для работы с пользователями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class GroupControllerServiceImpl(
    private val channelService: ChannelService,
    private val inviteService: InviteService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : GroupControllerService {
    override suspend fun changeOwner(id: UUID, ownerId: UUID, user: User): ChannelDto {
        // Изменять группу может только её владелец.
        channelService.checkGroupDmWriteAccess(id = id, userId = user.id)

        return channelService.changeOwner(id, ownerId).toDtoWithData()
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }
    }

    override suspend fun addRecipients(id: UUID, recipients: Set<UUID>, user: User): ChannelDto {
        // Изменять группу может только её владелец.
        channelService.checkGroupDmWriteAccess(id = id, userId = user.id)

        return channelService.addRecipients(id, recipients).toDtoWithData()
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }
    }

    override suspend fun removeRecipient(id: UUID, userId: UUID, user: User): ChannelDto {
        // Изменять группу может только её владелец.
        channelService.checkGroupDmWriteAccess(id = id, userId = user.id)

        return channelService.deleteRecipient(id, userId).toDtoWithData()
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }
    }

    override suspend fun createGroupInvite(id: UUID, user: User) =
        channelService.checkGroupDmWriteAccess(id = id, userId = user.id)
            .let { inviteService.saveInvite(Invite(authorId = user.id, channelId = id, type = GROUP_DM)) }
            .toDtoWithLinkData()
            .apply { userWsStorage.sendMessage(INVITE_CREATE, this) }

    /**
     * Возвращает DTO представление группы.
     */
    private suspend fun Channel.toDtoWithData() =
        this.toDto().apply {
            recipients = this@toDtoWithData.recipients
                ?.map { recipientId -> userService.findUser(recipientId) }
                ?.map(User::toDto)
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
}