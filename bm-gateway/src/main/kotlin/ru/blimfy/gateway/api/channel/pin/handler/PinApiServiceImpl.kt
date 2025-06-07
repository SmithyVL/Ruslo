package ru.blimfy.gateway.api.channel.pin.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.message.MessageService
import ru.blimfy.common.enumeration.ChannelGroups.SERVER
import ru.blimfy.common.enumeration.ChannelGroups.USER
import ru.blimfy.gateway.api.channel.dto.message.MessageDto
import ru.blimfy.gateway.api.mapper.MessageMapper
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.integration.websockets.dto.ChannelPinsUpdateDto
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_PINS_UPDATE

/**
 * Реализация интерфейса для работы с обработкой запросов о закреплённых сообщениях.
 *
 * @property messageService сервис для работы с сообщениями.
 * @property channelService сервис для работы с каналами.
 * @property serverService сервис для работы с серверами.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @property msgMapper маппер для сообщений.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class PinApiServiceImpl(
    private val messageService: MessageService,
    private val channelService: ChannelService,
    private val serverService: ServerService,
    private val userWsStorage: UserWebSocketStorage,
    private val msgMapper: MessageMapper,
) : PinApiService {
    override suspend fun findPins(channelId: UUID, user: User): Flow<MessageDto> {
        checkMessageViewAccess(id = channelId, userId = user.id)

        return messageService.findPinnedMessages(channelId).map { msgMapper.toDtoWithRelations(it) }
    }

    override suspend fun changePinned(channelId: UUID, messageId: UUID, pinned: Boolean, user: User) {
        val userId = user.id
        val serverId = checkPinsUpdateAccess(channelId, userId)

        messageService.setPinned(messageId, pinned).apply {
            // Отправляем WS событие о закреплении/откреплении сообщения в канале.
            val data = ChannelPinsUpdateDto(channelId = channelId, serverId = serverId)
            userWsStorage.sendMessage(CHANNEL_PINS_UPDATE, data)
        }
    }

    /**
     * Проверяет возможность закрепления сообщения в канале с [id] для пользователя с [userId] и возвращает
     * идентификатор сервера, если он есть.
     */
    private suspend fun checkPinsUpdateAccess(id: UUID, userId: UUID) =
        channelService.findChannel(id).let { channel ->
            channel.serverId.apply {
                when (channel.type.group) {
                    USER -> channelService.checkChannelView(id = channel.id, userId = userId)
                    SERVER -> serverService.checkServerWrite(serverId = this!!, userId = userId)
                }
            }
        }

    /**
     * Проверяет возможность просмотра сообщений в канале с [id] для пользователя с [userId] и возвращает
     * идентификатор сервера, если он есть.
     */
    private suspend fun checkMessageViewAccess(id: UUID, userId: UUID) =
        channelService.findChannel(id).let { channel ->
            channel.serverId.apply {
                when (channel.type.group) {
                    USER -> channelService.checkChannelView(id = channel.id, userId = userId)
                    SERVER -> serverService.checkServerView(serverId = this!!, userId = userId)
                }
            }
        }
}