package ru.blimfy.gateway.service.channel.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.channel.message.MessageDto
import ru.blimfy.gateway.dto.channel.message.ModifyMessageDto
import ru.blimfy.gateway.dto.channel.message.NewMessageDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о сообщениях каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MessageApiService {
    /**
     * Возвращает страницу с [limit] сообщений канала с [channelId], которые хочет получить [user]. Для поиска позиции
     * поиска используются параметры [around], [before] и [after].
     */
    suspend fun findMessages(
        channelId: UUID,
        around: UUID? = null,
        before: UUID? = null,
        after: UUID? = null,
        limit: Int,
        user: User,
    ): Flow<MessageDto>

    /**
     * Возвращает новое [message] в канале с [channelId] от [user].
     */
    suspend fun createMessage(channelId: UUID, message: NewMessageDto, user: User): MessageDto

    /**
     * Возвращает обновлённое [message] с [id] в канале с [channelId] от [user].
     */
    suspend fun editMessage(channelId: UUID, id: UUID, message: ModifyMessageDto, user: User): MessageDto

    /**
     * Удаляет сообщение с [id] в канале с [channelId] от [user].
     */
    suspend fun deleteMessage(channelId: UUID, id: UUID, user: User)
}