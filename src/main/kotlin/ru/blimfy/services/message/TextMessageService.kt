package ru.blimfy.services.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.persistence.entity.TextMessage

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
     * Возвращает сообщения канала с идентификатором [channelId].
     */
    suspend fun findChannelMessages(channelId: UUID): Flow<TextMessage>

    /**
     * Удаляет сообщение канала с таким [id].
     */
    suspend fun deleteMessage(id: UUID)
}