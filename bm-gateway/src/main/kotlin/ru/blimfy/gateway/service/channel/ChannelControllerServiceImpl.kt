package ru.blimfy.gateway.service.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.server.channel.ChannelDto
import ru.blimfy.gateway.dto.server.channel.ModifyChannelDto
import ru.blimfy.gateway.dto.server.channel.NewChannelDto
import ru.blimfy.gateway.dto.server.channel.toDto
import ru.blimfy.gateway.dto.server.channel.toEntity
import ru.blimfy.gateway.dto.server.message.TextMessageDto
import ru.blimfy.gateway.dto.server.message.toDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.channel.ChannelService
import ru.blimfy.server.usecase.message.TextMessageService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_CREATE
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_DELETE
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE

/**
 * Реализация интерфейса для работы с обработкой запросов о каналах серверов.
 *
 * @property channelService сервис для работы с каналами.
 * @property textMessageService сервис для работы с сообщениями каналов.
 * @property serverService сервис для работы с серверами.
 * @property userService сервис для работы с пользователями.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelControllerServiceImpl(
    private val channelService: ChannelService,
    private val textMessageService: TextMessageService,
    private val serverService: ServerService,
    private val userService: UserService,
    private val userWebSocketStorage: UserWebSocketStorage,
) : ChannelControllerService {
    override suspend fun createChannel(newChannelDto: NewChannelDto, currentUser: User): ChannelDto {
        val userId = currentUser.id

        // Создать канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = newChannelDto.serverId, userId = userId)

        return channelService.createChannel(newChannelDto.toEntity()).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, CHANNEL_CREATE, this, userId) }
    }

    override suspend fun modifyChannel(modifyChannel: ModifyChannelDto, currentUser: User): ChannelDto {
        val userId = currentUser.id
        val serverId = channelService.findChannel(modifyChannel.id).serverId

        // Обновить канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        channelService.modifyChannel(modifyChannel.id, modifyChannel.name, modifyChannel.nsfw)
        return channelService.findChannel(modifyChannel.id).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, CHANNEL_UPDATE, this, userId) }
    }

    override suspend fun deleteChannel(channelId: UUID, currentUser: User) {
        val userId = currentUser.id

        // Удалить канал сервера может только создатель сервера.
        val serverId = channelService.findChannel(channelId).serverId
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        channelService.deleteChannel(channelId = channelId, serverId = serverId)
            .apply { userWebSocketStorage.sendServerMessages(serverId, CHANNEL_DELETE, channelId, userId) }
    }

    override suspend fun findChannel(channelId: UUID, currentUser: User): ChannelDto {
        val channel = channelService.findChannel(channelId)

        // Получить информацию о канале сервера может только его участник.
        serverService.checkServerViewAccess(serverId = channel.serverId, userId = currentUser.id)

        return channel.toDto()
    }

    override suspend fun findChannelMessages(
        channelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        currentUser: User,
    ): Flow<TextMessageDto> {
        val userId = currentUser.id

        // Получить текстовые сообщения канала сервера может только его участник.
        val serverId = channelService.findChannel(channelId).serverId
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        return textMessageService.findPageChannelMessages(channelId, pageNumber, pageSize)
            .map { it.toDto() }
            .onEach { it.author = userService.findUser(userId).toDto() }
    }
}