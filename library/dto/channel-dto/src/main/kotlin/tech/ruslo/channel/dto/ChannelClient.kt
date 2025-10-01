package tech.ruslo.channel.dto

import kotlinx.coroutines.flow.Flow

/**
 * Клиентский интерфейс для работы с информацией о каналах из сервиса каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelClient {
    /**
     * Возвращает сохранённый [channelDto].
     */
    suspend fun saveChannel(channelDto: ChannelDto): ChannelDto

    /**
     * Возвращает сохранённые [channelDtos].
     */
    fun saveChannels(channelDtos: List<ChannelDto>): Flow<ChannelDto>

    /**
     * Удаляет канал с [id].
     */
    suspend fun deleteChannel(id: Long)
}