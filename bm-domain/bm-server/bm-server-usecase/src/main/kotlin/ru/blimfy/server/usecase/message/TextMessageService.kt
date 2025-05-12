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
     * Возвращает новый или обновлённый [message].
     */
    suspend fun saveMessage(message: TextMessage): TextMessage

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