package ru.blimfy.domain.channel.usecase.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.domain.channel.api.dto.message.NewMessageDto
import ru.blimfy.domain.channel.db.entity.Message

/**
 * Интерфейс для работы с сообщениями каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MessageService {
    /**
     * Возвращает сообщение, созданное по информации из [newMessageDto], в канале с [channelId] от пользователя с
     * [userId].
     */
    suspend fun createMessage(newMessageDto: NewMessageDto, channelId: UUID, userId: UUID): Message

    /**
     * Возвращает страницу с [limit] сообщений канала с [channelId]. Для поиска позиции используются параметры [around],
     * [before] и [after].
     */
    suspend fun findMessages(
        channelId: UUID,
        around: UUID? = null,
        before: UUID? = null,
        after: UUID? = null,
        limit: Int,
    ): Flow<Message>

    /**
     * Возвращает закреплённые сообщения канала с [channelId].
     */
    suspend fun findPinnedMessages(channelId: UUID): Flow<Message>

    /**
     * Возвращает сообщение с [id] и новым [content].
     */
    suspend fun setContent(id: UUID, content: String): Message

    /**
     * Изменяет флаг закреплённости сообщения с [id] на значение - [pinned].
     */
    suspend fun setPinned(id: UUID, pinned: Boolean)

    /**
     * Удаляет сообщение с [id] от [authorId].
     */
    suspend fun deleteMessage(id: UUID, authorId: UUID)
}