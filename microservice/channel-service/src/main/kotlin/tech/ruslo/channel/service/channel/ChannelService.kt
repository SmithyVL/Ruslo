package tech.ruslo.channel.service.channel

import kotlinx.coroutines.flow.Flow
import tech.ruslo.channel.database.entity.Channel

/**
 * Интерфейс для работы с каналами.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelService {
    /**
     * Возвращает сохранённый [channel].
     */
    suspend fun saveChannel(channel: Channel): Channel

    /**
     * Возвращает сохранённые [channels].
     */
    fun saveChannels(channels: List<Channel>): Flow<Channel>

    /**
     * Удаляет канал с [id].
     */
    suspend fun deleteChannel(id: Long)
}