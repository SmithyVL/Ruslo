package tech.ruslo.gateway.api.channel.group.handler

import java.util.UUID
import org.springframework.stereotype.Service
import tech.ruslo.domain.channel.db.entity.Invite
import tech.ruslo.domain.channel.db.entity.InviteTypes.GROUP_DM
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.channel.usecase.invite.InviteService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.mapper.ChannelMapper
import tech.ruslo.gateway.mapper.InviteMapper
import tech.ruslo.websocket.dto.enums.SendEvents.CHANNEL_UPDATE
import tech.ruslo.websocket.dto.enums.SendEvents.INVITE_CREATE
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о группах.
 *
 * @property accessService сервис для работы с доступами.
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
    private val accessService: AccessService,
    private val channelService: ChannelService,
    private val inviteService: InviteService,
    private val channelMapper: ChannelMapper,
    private val inviteMapper: InviteMapper,
    private val userWsStorage: UserWebSocketStorage,
) : GroupApiService {
    override suspend fun changeOwner(id: UUID, ownerId: UUID, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { channelService.changeOwner(id, ownerId) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE.name, this) }

    override suspend fun addRecipients(id: UUID, recipients: Set<UUID>, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { channelService.addRecipients(id, recipients) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE.name, this) }

    override suspend fun removeRecipient(id: UUID, userId: UUID, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { channelService.deleteRecipient(id, userId) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_UPDATE.name, this) }

    override suspend fun createInvite(id: UUID, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { Invite(user.id, id, GROUP_DM) }
            .let { inviteService.createInvite(it) }
            .let { inviteMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(INVITE_CREATE.name, this) }
}