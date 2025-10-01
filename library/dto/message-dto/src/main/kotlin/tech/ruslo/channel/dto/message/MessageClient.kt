package tech.ruslo.channel.dto.message

import kotlinx.coroutines.flow.Flow

/**
 * Клиентский интерфейс для работы с информацией о сообщениях из сервиса каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MessageClient {
    /**
     * Возвращает сохранённое [messageDto].
     */
    suspend fun saveMessage(messageDto: MessageDto): MessageDto

    /**
     * Возвращает страницу с сообщениями канала с [channelId] после поиска с помощью параметров [around], [before],
     * [after] и [limit].
     */
    fun findMessages(
        channelId: Long,
        around: Long? = null,
        before: Long? = null,
        after: Long? = null,
        limit: Int = 50,
    ): Flow<MessageDto>

    /**
     * Удаляет сообщение с [id].
     */
    suspend fun deleteMessage(id: Long)
}