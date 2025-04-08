package ru.blimfy.services.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.common.dto.ChannelDto

/**
 * Интерфейс для работы с каналами серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelService {
    /**
     * Возвращает новый или обновлённый [channel].
     */
    suspend fun saveChannel(channel: ChannelDto): ChannelDto

    /**
     * Возвращает существующий канал с [id].
     */
    suspend fun findChannel(id: UUID): ChannelDto

    /**
     * Возвращает каналы сервера с [serverId].
     */
    fun findServerChannels(serverId: UUID): Flow<ChannelDto>

    /**
     * Удаляет канал с таким [id].
     */
    suspend fun deleteChannel(id: UUID)
}