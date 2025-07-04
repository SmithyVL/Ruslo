package ru.blimfy.gateway.api.server.handler

import java.util.UUID
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.domain.channel.api.dto.channel.ChannelPositionDto
import ru.blimfy.domain.channel.api.dto.channel.NewChannelDto
import ru.blimfy.domain.channel.db.entity.Invite
import ru.blimfy.domain.channel.db.entity.InviteTypes.SERVER
import ru.blimfy.domain.channel.usecase.channel.ChannelService
import ru.blimfy.domain.channel.usecase.invite.InviteService
import ru.blimfy.domain.server.api.dto.server.ModifyServerDto
import ru.blimfy.domain.server.api.dto.server.NewServerDto
import ru.blimfy.domain.server.usecase.server.ServerService
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.access.control.service.AccessService
import ru.blimfy.gateway.mapper.InviteMapper
import ru.blimfy.gateway.mapper.ServerMapper
import ru.blimfy.gateway.mapper.toBasicDto
import ru.blimfy.gateway.mapper.toDto
import ru.blimfy.websocket.dto.enums.SendEvents.CHANNEL_CREATE
import ru.blimfy.websocket.dto.enums.SendEvents.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.enums.SendEvents.SERVER_DELETE
import ru.blimfy.websocket.dto.enums.SendEvents.SERVER_UPDATE
import ru.blimfy.websocket.storage.UserWebSocketStorage

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