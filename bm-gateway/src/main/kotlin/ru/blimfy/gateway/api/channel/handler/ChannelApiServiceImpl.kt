package ru.blimfy.gateway.api.channel.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.InviteTypes.GROUP_DM
import ru.blimfy.gateway.api.channel.dto.ModifyChannelDto
import ru.blimfy.gateway.api.mapper.ChannelMapper
import ru.blimfy.gateway.api.mapper.InviteMapper
import ru.blimfy.gateway.api.mapper.MemberMapper
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.integration.websockets.dto.TypingStartDto
import ru.blimfy.gateway.service.AccessService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_DELETE
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.WsMessageTypes.INVITE_CREATE
import ru.blimfy.websocket.dto.WsMessageTypes.TYPING_START

/**
 * Реализация интерфейса для работы с обработкой запросов о личных каналах.
 *
 * @property accessService сервис для работы с доступами.
 * @property channelService сервис для работы с каналами.
 * @property inviteService сервис для работы с приглашениями.
 * @property channelMapper маппер для работы с каналами.
 * @property inviteMapper маппер для работы с приглашениями.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelApiServiceImpl(
    private val accessService: AccessService,
    private val channelService: ChannelService,
    private val inviteService: InviteService,
    private val channelMapper: ChannelMapper,
    private val inviteMapper: InviteMapper,
    private val memberMapper: MemberMapper,
    private val userWsStorage: UserWebSocketStorage,
) : ChannelApiService {
    override suspend fun findChannel(id: UUID, user: User) =
        accessService.isChannelViewAccess(id, user.id)
            .let { channelService.findChannel(id) }
            .let { channelMapper.toDto(it) }

    override suspend fun modifyChannel(id: UUID, modifyChannel: ModifyChannelDto, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { channelMapper.toEntity(modifyChannel, id) }
            .let { channelService.save(it) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }

    override suspend fun deleteChannel(id: UUID, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { channelService.deleteChannel(id) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_DELETE, id) }

    override suspend fun triggerTypingIndicator(id: UUID, user: User) {
        TypingStartDto(id, user.id)
            .apply { member = memberMapper.toWsDto(id, user.id) }
            .let { userWsStorage.sendMessage(TYPING_START, it) }
    }

    override suspend fun findInvites(id: UUID, user: User) =
        accessService.isChannelViewAccess(id, user.id)
            .let { inviteService.findInvites(id) }
            .map { inviteMapper.toDto(it) }

    override suspend fun createGroupInvite(id: UUID, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { inviteService.saveInvite(Invite(user.id, id, GROUP_DM)) }
            .let { inviteMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(INVITE_CREATE, this) }
}