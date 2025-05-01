package ru.blimfy.server.usecase.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
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
     * Возвращает страницу с сообщениями канала с идентификатором [channelId] по конфигурации [pageable].
     */
    suspend fun findPageChannelMessages(channelId: UUID, pageable: Pageable): Flow<TextMessage>

    /**
     * Удаляет сообщение канала с [id] от [authorId].
     */
    suspend fun deleteMessage(id: UUID, authorId: UUID)
}