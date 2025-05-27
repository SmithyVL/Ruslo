package ru.blimfy.gateway.service.channel

import java.time.Instant.now
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.ChannelGroups.SERVER
import ru.blimfy.common.enumeration.ChannelGroups.USER
import ru.blimfy.common.enumeration.InviteTypes.GROUP_DM
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.ModifyChannelDto
import ru.blimfy.gateway.dto.channel.invite.InviteDto
import ru.blimfy.gateway.dto.channel.invite.toDto
import ru.blimfy.gateway.dto.channel.toDto
import ru.blimfy.gateway.dto.channel.toEntity
import ru.blimfy.gateway.dto.channel.toPartialDto
import ru.blimfy.gateway.dto.server.toPartialDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.integration.websockets.base.PartialMemberDto
import ru.blimfy.gateway.integration.websockets.dto.TypingStartDto
import ru.blimfy.gateway.integration.websockets.extra.MemberInfoDto
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_DELETE
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.WsMessageTypes.INVITE_CREATE
import ru.blimfy.websocket.dto.WsMessageTypes.TYPING_START

/**
 * Реализация интерфейса для работы с обработкой запросов о личных каналах.
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
class ChannelApiServiceImpl(
    private val channelService: ChannelService,
    private val inviteService: InviteService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : ChannelApiService {
    override suspend fun findChannel(id: UUID, user: User): ChannelDto {
        checkChannelViewAccess(id = id, userId = user.id)

        return channelService.findChannel(id).toDtoWithData()
    }

    override suspend fun modifyChannel(id: UUID, modifyChannel: ModifyChannelDto, user: User): ChannelDto {
        checkChannelWriteAccess(id = id, userId = user.id)

        val channel = modifyChannel.toEntity(channelService.findChannel(id))
        return channelService.save(channel).toDtoWithData()
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }
    }

    override suspend fun deleteChannel(id: UUID, user: User): ChannelDto {
        checkChannelWriteAccess(id = id, userId = user.id)

        return channelService.deleteChannel(id).toDtoWithData()
            .apply { userWsStorage.sendMessage(CHANNEL_DELETE, id) }
    }

    override suspend fun triggerTypingIndicator(id: UUID, user: User) {
        val memberInfo = channelService.findChannel(id).serverId?.let { serverId ->
            val memberNick = memberService.findServerMember(serverId = serverId, userId = user.id).nick
            MemberInfoDto(serverId, PartialMemberDto(memberNick))
        }

        val data = TypingStartDto(id, user.id, now(), memberInfo)
        userWsStorage.sendMessage(TYPING_START, data)
    }

    override suspend fun findInvites(id: UUID, user: User): Flow<InviteDto> {
        checkChannelViewAccess(id = id, userId = user.id)

        return inviteService.findInvites(id).map { it.toDtoWithLinkData() }
    }

    override suspend fun createGroupInvite(id: UUID, user: User) =
        checkChannelViewAccess(id = id, userId = user.id)
            .let { inviteService.saveInvite(Invite(authorId = user.id, channelId = id, type = GROUP_DM)) }
            .toDtoWithLinkData()
            .apply { userWsStorage.sendMessage(INVITE_CREATE, this) }

    /**
     * Проверяет возможность просмотра канала с [id] для пользователя с [userId] и возвращает идентификатор сервера,
     * если он есть.
     */
    private suspend fun checkChannelViewAccess(id: UUID, userId: UUID) =
        channelService.findChannel(id).let { channel ->
            channel.serverId.apply {
                when (channel.type.group) {
                    USER -> channelService.checkChannelViewAccess(id = channel.id, userId = userId)
                    SERVER -> serverService.checkServerViewAccess(serverId = this!!, userId = userId)
                }
            }
        }

    /**
     * Проверяет возможность изменения канала с [id] для пользователя с [userId] и возвращает идентификатор сервера,
     * если он есть.
     */
    private suspend fun checkChannelWriteAccess(id: UUID, userId: UUID) =
        channelService.findChannel(id).let { channel ->
            channel.serverId.apply {
                when (channel.type.group) {
                    USER -> channelService.checkGroupDmWriteAccess(id = channel.id, userId = userId)
                    SERVER -> serverService.checkServerModifyAccess(serverId = this!!, userId = userId)
                }
            }
        }

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