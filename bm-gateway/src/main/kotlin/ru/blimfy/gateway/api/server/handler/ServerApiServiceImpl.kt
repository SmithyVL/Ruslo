package ru.blimfy.gateway.api.server.handler

import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.ChannelTypes.TEXT
import ru.blimfy.common.enumeration.ChannelTypes.VOICE
import ru.blimfy.common.enumeration.InviteTypes.SERVER
import ru.blimfy.gateway.api.dto.channel.NewChannelDto
import ru.blimfy.gateway.api.mapper.ChannelMapper
import ru.blimfy.gateway.api.mapper.InviteMapper
import ru.blimfy.gateway.api.mapper.ServerMapper
import ru.blimfy.gateway.api.server.dto.ModifyServerDto
import ru.blimfy.gateway.api.server.dto.NewServerDto
import ru.blimfy.gateway.api.server.dto.channel.ChannelPositionDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.service.AccessService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.*
import java.util.*

/**
 * Реализация интерфейса для работы с обработкой запросов о серверах.
 *
 * @property accessService сервис для работы с доступами.
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами серверов.
 * @property inviteService сервис для работы с приглашениями серверов.
 * @property serverMapper маппер для работы с серверами.
 * @property channelMapper маппер для работы с каналами.
 * @property inviteMapper маппер для работы с приглашениями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerApiServiceImpl(
    private val accessService: AccessService,
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val inviteService: InviteService,
    private val serverMapper: ServerMapper,
    private val channelMapper: ChannelMapper,
    private val inviteMapper: InviteMapper,
    private val userWsStorage: UserWebSocketStorage,
) : ServerApiService {
    @Transactional
    override suspend fun createServer(newServer: NewServerDto, user: User) =
        user.id.let { userId ->
            serverService.createServer(serverMapper.toEntity(newServer, userId))
                .apply { createDefaultServerChannels(id) }
                .let { serverMapper.toDto(it, userId) }
        }

    override suspend fun findServer(id: UUID, user: User) =
        user.id.let { userId ->
            accessService.isServerMember(id, userId)
                .let { serverService.findServer(id) }
                .let { serverMapper.toDto(it, userId) }
        }

    override suspend fun modifyServer(id: UUID, modifyServer: ModifyServerDto, user: User) =
        user.id.let { userId ->
            accessService.isServerOwner(id, userId)
                .let {
                    serverService.modifyServer(
                        id,
                        modifyServer.name,
                        modifyServer.icon,
                        modifyServer.bannerColor,
                        modifyServer.description
                    )
                }
                .let { serverMapper.toDto(it, userId) }
                .apply { userWsStorage.sendMessage(SERVER_UPDATE, this) }
        }

    override suspend fun changeOwner(id: UUID, userId: UUID, user: User) =
        user.id.let { userId ->
            accessService.isServerOwner(id, userId)
                .let { serverService.setOwner(id = id, ownerId = userId) }
                .let { serverMapper.toDto(it, userId) }
                .apply { userWsStorage.sendMessage(SERVER_UPDATE, this) }
        }

    override suspend fun deleteServer(id: UUID, user: User) =
        serverService.deleteServer(id, user.id)

    override suspend fun findServerChannels(id: UUID, user: User) =
        accessService.isServerMember(id, user.id)
            .let { channelService.findServerChannels(id) }
            .map { channelMapper.toDto(it) }

    override suspend fun createChannel(id: UUID, channelDto: NewChannelDto, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { channelService.save(channelMapper.toEntity(channelDto, null, id)) }
            .let { channelMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(CHANNEL_CREATE, this) }

    override suspend fun modifyServerChannelPositions(id: UUID, positions: List<ChannelPositionDto>, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { positions }
            .forEach { positionInfo ->
                channelService.findChannel(positionInfo.id)
                    .apply {
                        position = positionInfo.position
                        parentId = positionInfo.parentId
                    }
                    .let { channelService.save(it) }
                    .let { channelMapper.toDto(it) }
                    .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }
            }

    override suspend fun findServerInvites(id: UUID, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { inviteService.findInvites(id, SERVER) }
            .map { inviteMapper.toDto(it) }

    override suspend fun createInvite(id: UUID, channelId: UUID, user: User) =
        accessService.isServerOwner(id, user.id)
            .let { Invite(user.id, channelId, SERVER).apply { serverId = id } }
            .let { inviteService.saveInvite(it) }
            .let { inviteMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(INVITE_CREATE, this) }

    /**
     * Создаёт набор стандартных каналов для сервера с [serverId].
     */
    private suspend fun createDefaultServerChannels(serverId: UUID) {
        Channel(TEXT, serverId).apply {
            name = SERVER_DEFAULT_TEXT_CHANNEL
            channelService.save(this)
        }

        Channel(VOICE, serverId).apply {
            name = SERVER_DEFAULT_VOICE_CHANNEL
            channelService.save(this)
        }
    }

    private companion object {
        /**
         * Стандартное название текстового канала сервера.
         */
        private const val SERVER_DEFAULT_TEXT_CHANNEL = "Текстовый канал"

        /**
         * Стандартное название голосового канала сервера.
         */
        private const val SERVER_DEFAULT_VOICE_CHANNEL = "Голосовой канал"
    }
}