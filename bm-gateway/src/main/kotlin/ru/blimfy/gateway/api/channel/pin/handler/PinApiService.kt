package ru.blimfy.gateway.api.channel.pin.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.api.channel.dto.message.MessageDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о закреплённых сообщениях в канале.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface PinApiService {
    /**
     * Возвращает закреплённые сообщения канала с [channelId], которые хочет получить [user].
     */
    suspend fun findPins(channelId: UUID, user: User): Flow<MessageDto>

    /**
     * Закрепляет сообщение с [messageId] и новым значением [pinned] в канале с [channelId], обновляемое [user].
     */
    suspend fun changePinned(channelId: UUID, messageId: UUID, pinned: Boolean, user: User)
}