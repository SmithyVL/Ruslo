package tech.ruslo.gateway.api.channel.message.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import tech.ruslo.domain.channel.api.dto.message.ModifyMessageDto
import tech.ruslo.domain.channel.api.dto.message.NewMessageDto
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.dto.message.MessageDto

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
     * Возвращает новое [newMessageDto] в канале с [channelId] от [user].
     */
    suspend fun createMessage(channelId: UUID, newMessageDto: NewMessageDto, user: User): MessageDto

    /**
     * Возвращает обновлённое [modifyMessageDto] с [id] в канале с [channelId] от [user].
     */
    suspend fun editMessage(channelId: UUID, id: UUID, modifyMessageDto: ModifyMessageDto, user: User): MessageDto

    /**
     * Удаляет сообщение с [id] в канале с [channelId] от [user].
     */
    suspend fun deleteMessage(channelId: UUID, id: UUID, user: User)
}