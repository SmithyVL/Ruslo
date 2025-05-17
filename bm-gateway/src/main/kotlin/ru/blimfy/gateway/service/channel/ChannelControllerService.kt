package ru.blimfy.gateway.service.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.server.channel.ChannelDto
import ru.blimfy.gateway.dto.server.channel.ModifyChannelDto
import ru.blimfy.gateway.dto.server.channel.NewChannelDto
import ru.blimfy.gateway.dto.server.message.TextMessageDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о каналах серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelControllerService {
    /**
     * Возвращает новый [newChannelDto], который создаёт [currentUser].
     */
    suspend fun createChannel(newChannelDto: NewChannelDto, currentUser: User): ChannelDto

    /**
     * Возвращает обновлённый [modifyChannel], который обновляет [currentUser].
     */
    suspend fun modifyChannel(modifyChannel: ModifyChannelDto, currentUser: User): ChannelDto

    /**
     * Удаляет канал с [channelId], который удаляет [currentUser].
     */
    suspend fun deleteChannel(channelId: UUID, currentUser: User)

    /**
     * Возвращает канал с [channelId], который хочет получить [currentUser].
     */
    suspend fun findChannel(channelId: UUID, currentUser: User): ChannelDto

    /**
     * Возвращает [pageNumber] страницу с [pageSize] сообщениями канала с [channelId], которые хочет получить
     * [currentUser].
     */
    suspend fun findChannelMessages(
        channelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        currentUser: User,
    ): Flow<TextMessageDto>
}