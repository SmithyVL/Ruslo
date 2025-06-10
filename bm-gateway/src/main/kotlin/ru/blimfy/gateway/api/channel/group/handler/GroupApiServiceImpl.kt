package ru.blimfy.gateway.api.channel.group.handler

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.InviteTypes.GROUP_DM
import ru.blimfy.gateway.api.mapper.ChannelMapper
import ru.blimfy.gateway.api.mapper.InviteMapper
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.WsMessageTypes.INVITE_CREATE

/**
 * Реализация интерфейса для работы с обработкой запросов о группах.
 *
 * @property channelService сервис для работы с каналами.
 * @property inviteService сервис для работы с приглашениями.
 * @property channelMapper маппер для работы с каналами.
 * @property inviteMapper маппер для работы с приглашениями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class GroupApiServiceImpl(
    private val channelService: ChannelService,
    private val inviteService: InviteService,
    private val channelMapper: ChannelMapper,
    private val inviteMapper: InviteMapper,
    private val userWsStorage: UserWebSocketStorage,
) : GroupApiService {
    override suspend fun changeOwner(id: UUID, ownerId: UUID, user: User) =
        // Изменять группу может только её владелец.
        channelService.checkGroupDmWrite(id, user.id)
            .let { channelService.changeOwner(id, ownerId) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }

    override suspend fun addRecipients(id: UUID, recipients: Set<UUID>, user: User) =
        // Изменять группу может только её владелец.
        channelService.checkGroupDmWrite(id, user.id)
            .let { channelService.addRecipients(id, recipients) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }

    override suspend fun removeRecipient(id: UUID, userId: UUID, user: User) =
        // Изменять группу может только её владелец.
        channelService.checkGroupDmWrite(id, user.id)
            .let { channelService.deleteRecipient(id, userId) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }

    override suspend fun createGroupInvite(id: UUID, user: User) =
        // Создать приглашение в группу может только её владелец.
        channelService.checkGroupDmWrite(id, user.id)
            .let { inviteService.saveInvite(Invite(user.id, id, GROUP_DM)) }
            .let { inviteMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(INVITE_CREATE, this) }
}