package ru.blimfy.server.usecase.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.server.db.entity.TextMessage

/**
 * Интерфейс для работы с сообщениями текстовых каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface TextMessageService {
    /**
     * Возвращает новое [message].
     */
    suspend fun createMessage(message: TextMessage): TextMessage

    /**
     * Возвращает обновлённое сообщение с [id] и [newContent].
     */
    suspend fun setContent(id: UUID, newContent: String): TextMessage

    /**
     * Возвращает обновлённое сообщение с [id] и [newPinned].
     */
    suspend fun setPinned(id: UUID, newPinned: Boolean): TextMessage

    /**
     * Возвращает существующее текстовое сообщение с [id].
     */
    suspend fun findMessage(id: UUID): TextMessage

    /**
     * Возвращает [pageNumber] страницу с [pageSize] сообщениями канала с [channelId].
     */
    suspend fun findPageChannelMessages(channelId: UUID, pageNumber: Int, pageSize: Int): Flow<TextMessage>

    /**
     * Удаляет сообщение канала с [textMessageId] от [authorId].
     */
    suspend fun deleteMessage(textMessageId: UUID, authorId: UUID)
}