package ru.blimfy.gateway.service.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.NewChannelDto
import ru.blimfy.gateway.dto.channel.toDto
import ru.blimfy.gateway.dto.channel.toEntity
import ru.blimfy.gateway.dto.message.text.TextMessageDto
import ru.blimfy.gateway.dto.message.text.toDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.channel.ChannelService
import ru.blimfy.server.usecase.message.TextMessageService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_SERVER_CHANNEL
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_SERVER_CHANNEL
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_SERVER_CHANNEL

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
    override suspend fun createChannel(newChannelDto: NewChannelDto, user: CustomUserDetails): ChannelDto {
        val userId = user.userInfo.id

        // Создать канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = newChannelDto.serverId, userId = userId)

        return channelService.saveChannel(newChannelDto.toEntity()).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, NEW_SERVER_CHANNEL, this, userId) }
    }

    override suspend fun modifyChannel(channelDto: ChannelDto, user: CustomUserDetails): ChannelDto {
        val userId = user.userInfo.id

        // Обновить канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = channelDto.serverId, userId = userId)

        return channelService.saveChannel(channelDto.toEntity()).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, EDIT_SERVER_CHANNEL, this, userId) }
    }

    override suspend fun deleteChannel(channelId: UUID, user: CustomUserDetails) {
        val userId = user.userInfo.id

        // Удалить канал сервера может только создатель сервера.
        val serverId = channelService.findChannel(channelId).serverId
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        channelService.deleteChannel(channelId = channelId, serverId = serverId)
            .apply { userWebSocketStorage.sendServerMessages(serverId, REMOVE_SERVER_CHANNEL, channelId, userId) }
    }

    override suspend fun findChannel(channelId: UUID, user: CustomUserDetails): ChannelDto {
        val channel = channelService.findChannel(channelId)

        // Получить информацию о канале сервера может только его участник.
        serverService.checkServerViewAccess(serverId = channel.serverId, userId = user.userInfo.id)

        return channel.toDto()
    }

    override suspend fun findChannelMessages(
        channelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        user: CustomUserDetails,
    ): Flow<TextMessageDto> {
        val userId = user.userInfo.id

        // Получить текстовые сообщения канала сервера может только его участник.
        val serverId = channelService.findChannel(channelId).serverId
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        return textMessageService.findPageChannelMessages(channelId, pageNumber, pageSize)
            .map { it.toDto() }
            .onEach { it.author = userService.findUser(userId).toDto() }
    }
}