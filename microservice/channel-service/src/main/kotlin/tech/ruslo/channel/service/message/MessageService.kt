package tech.ruslo.channel.service.message

import kotlinx.coroutines.flow.Flow
import tech.ruslo.channel.database.entity.Message

/**
 * Интерфейс для работы с сообщениями каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MessageService {
    /**
     * Возвращает сохранённое [message].
     */
    suspend fun saveMessage(message: Message): Message

    /**
     * Возвращает страницу с [limit] сообщений канала с [channelId]. Для поиска позиции используются параметры [around],
     * [before] и [after].
     */
    fun findMessages(
        channelId: Long,
        around: Long? = null,
        before: Long? = null,
        after: Long? = null,
        limit: Int,
    ): Flow<Message>

    /**
     * Удаляет сообщение с [id].
     */
    suspend fun deleteMessage(id: Long)
}