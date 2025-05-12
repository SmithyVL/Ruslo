package ru.blimfy.gateway.service.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.NewChannelDto
import ru.blimfy.gateway.dto.message.text.TextMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Интерфейс для работы с обработкой запросов о каналах серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelControllerService {
    /**
     * Возвращает новый [newChannelDto], который создаёт [user].
     */
    suspend fun createChannel(newChannelDto: NewChannelDto, user: CustomUserDetails): ChannelDto

    /**
     * Возвращает обновлённый [channelDto], который обновляет [user].
     */
    suspend fun modifyChannel(channelDto: ChannelDto, user: CustomUserDetails): ChannelDto

    /**
     * Удаляет канал с [channelId], который удаляет [user].
     */
    suspend fun deleteChannel(channelId: UUID, user: CustomUserDetails)

    /**
     * Возвращает канал с [channelId], который хочет получить [user].
     */
    suspend fun findChannel(channelId: UUID, user: CustomUserDetails): ChannelDto

    /**
     * Возвращает [pageNumber] страницу с [pageSize] сообщениями канала с [channelId], которые хочет получить [user].
     */
    suspend fun findChannelMessages(
        channelId: UUID,
        pageNumber: Int,
        pageSize: Int,
        user: CustomUserDetails,
    ): Flow<TextMessageDto>
}