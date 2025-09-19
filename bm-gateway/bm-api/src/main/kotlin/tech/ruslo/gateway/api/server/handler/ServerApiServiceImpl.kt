package tech.ruslo.gateway.api.server.handler

import java.util.UUID
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.ruslo.domain.channel.api.dto.channel.ChannelPositionDto
import tech.ruslo.domain.channel.api.dto.channel.NewChannelDto
import tech.ruslo.domain.channel.db.entity.Invite
import tech.ruslo.domain.channel.db.entity.InviteTypes.SERVER
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.channel.usecase.invite.InviteService
import tech.ruslo.domain.server.api.dto.server.ModifyServerDto
import tech.ruslo.domain.server.api.dto.server.NewServerDto
import tech.ruslo.domain.server.usecase.server.ServerService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.mapper.InviteMapper
import tech.ruslo.gateway.mapper.ServerMapper
import tech.ruslo.gateway.mapper.toBasicDto
import tech.ruslo.gateway.mapper.toDto
import tech.ruslo.websocket.dto.enums.SendEvents.CHANNEL_CREATE
import tech.ruslo.websocket.dto.enums.SendEvents.CHANNEL_UPDATE
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_DELETE
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_UPDATE
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о серверах.
 *
 * @property accessService сервис для работы с доступами.
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами серверов.
 * @property inviteService сервис для работы с приглашениями серверов.
 * @property inviteMapper маппер для работы с приглашениями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerApiServiceImpl(
    private val accessService: AccessService,
    private val serverService: ServerService,
    private val serverMapper: ServerMapper,
    private val channelService: ChannelService,
    private val inviteService: InviteService,
    private val inviteMapper: InviteMapper,
    private val userWsStorage: UserWebSocketStorage,
) : ServerApiService {
    @Transactional
    override suspend fun createServer(newServerDto: NewServerDto, user: User) =
        serverService.createServer(newServerDto, user.id)
            .let { serverMapper.toDto(it) }
            .apply { channelService.createDefaultChannels(id) }

    override suspend fun modifyServer(id: UUID, modifyServer: ModifyServerDto, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { serverService.modifyServer(id, modifyServer) }
            .toDto()
            .apply { userWsStorage.sendMessage(SERVER_UPDATE.name, this) }

    override suspend fun changeOwner(id: UUID, userId: UUID, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { serverService.modifyOwner(id, userId) }
            .toDto()
            .apply { userWsStorage.sendMessage(SERVER_UPDATE.name, this) }

    override suspend fun deleteServer(id: UUID, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { serverService.deleteServer(id) }
            .apply { userWsStorage.sendMessage(SERVER_DELETE.name, id) }

    override suspend fun createChannel(id: UUID, newChannelDto: NewChannelDto, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { channelService.createChannel(newChannelDto, serverId = id) }
            .toBasicDto()
            .apply { userWsStorage.sendMessage(CHANNEL_CREATE.name, this) }

    override suspend fun modifyChannelPositions(id: UUID, positions: List<ChannelPositionDto>, user: User) {
        accessService.isServerOwner(id, user.id)
            .let { channelService.modifyPositions(positions, id) }
            .map { it.toBasicDto() }
            .onEach { userWsStorage.sendMessage(CHANNEL_UPDATE.name, this) }
            .collect()
    }

    override suspend fun findInvites(id: UUID, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { inviteService.findServerInvites(id) }
            .map { inviteMapper.toDto(it) }

    override suspend fun createInvite(id: UUID, channelId: UUID, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { Invite(user.id, channelId, SERVER).apply { serverId = id } }
            .let { inviteService.createInvite(it) }
            .let { inviteMapper.toDto(it) }
}