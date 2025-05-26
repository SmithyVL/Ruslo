package ru.blimfy.gateway.service.channel.pin

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.channel.message.MessageDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о закреплённых сообщениях в канале.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface PinControllerService {
    /**
     * Возвращает [pageNumber] страницу с [pageSize] закреплённых сообщений канала с [channelId], которые хочет получить
     * [user].
     */
    suspend fun findPins(channelId: UUID, pageNumber: Int, pageSize: Int, user: User): Flow<MessageDto>

    /**
     * Закрепляет сообщение с [messageId] и новым значением [pinned] в канале с [channelId], обновляемое [user].
     */
    suspend fun changePinned(channelId: UUID, messageId: UUID, pinned: Boolean, user: User)
}