package ru.blimfy.channel.usecase.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.channel.db.entity.Message

/**
 * Интерфейс для работы с сообщениями каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MessageService {
    /**
     * Возвращает новое [message].
     */
    suspend fun createMessage(message: Message): Message

    /**
     * Возвращает сообщение с [id] и новым [content].
     */
    suspend fun setContent(id: UUID, content: String): Message

    /**
     * Изменяет флаг закреплённости сообщения с [id] на значение - [pinned].
     */
    suspend fun setPinned(id: UUID, pinned: Boolean)

    /**
     * Возвращает сообщение с [id].
     */
    suspend fun findMessage(id: UUID): Message

    /**
     * Возвращает максимальную позицию сообщения в канале с [channelId].
     */
    suspend fun findMaxChannelPosition(channelId: UUID): Long

    /**
     * Возвращает страницу из [limit] сообщений для канала с [channelId] с позиции [start] по [end].
     */
    fun findMessages(channelId: UUID, start: Long, end: Long, limit: Int): Flow<Message>

    /**
     * Возвращает закреплённые сообщения канала с [channelId].
     */
    suspend fun findPinnedMessages(channelId: UUID): Flow<Message>

    /**
     * Удаляет сообщение с [id] от [authorId].
     */
    suspend fun deleteMessage(id: UUID, authorId: UUID)

    /**
     * Удаляет сообщения канала с [channelId].
     */
    suspend fun deleteChannelMessages(channelId: UUID)
}