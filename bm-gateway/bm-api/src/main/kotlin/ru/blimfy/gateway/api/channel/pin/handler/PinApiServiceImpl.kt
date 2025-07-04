package ru.blimfy.gateway.api.channel.pin.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.domain.channel.usecase.channel.ChannelService
import ru.blimfy.domain.channel.usecase.message.MessageService
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.access.control.service.AccessService
import ru.blimfy.gateway.dto.websockets.PinsUpdateDto
import ru.blimfy.gateway.mapper.MessageMapper
import ru.blimfy.websocket.dto.enums.SendEvents.CHANNEL_PINS_UPDATE
import ru.blimfy.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о закреплённых сообщениях.
 *
 * @property accessService сервис для работы с доступами.
 * @property messageService сервис для работы с сообщениями.
 * @property channelService сервис для работы с каналами.
 * @property messageMapper маппер для сообщений.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class PinApiServiceImpl(
    private val accessService: AccessService,
    private val messageService: MessageService,
    private val channelService: ChannelService,
    private val messageMapper: MessageMapper,
    private val userWsStorage: UserWebSocketStorage,
) : PinApiService {
    override suspend fun findPins(channelId: UUID, user: User) =
        accessService.isChannelViewAccess(channelId, user.id)
            .let { messageService.findPinnedMessages(channelId) }
            .map { messageMapper.toDto(it) }

    override suspend fun changePinned(channelId: UUID, messageId: UUID, pinned: Boolean, user: User) {
        user.id.let { userId ->
            accessService.isPinsWriteAccess(channelId, userId)
                .let { messageService.setPinned(messageId, pinned) }
                .let { PinsUpdateDto(channelId, channelService.findChannel(channelId).serverId) }
                .let { userWsStorage.sendMessage(CHANNEL_PINS_UPDATE.name, it) }
        }
    }
}