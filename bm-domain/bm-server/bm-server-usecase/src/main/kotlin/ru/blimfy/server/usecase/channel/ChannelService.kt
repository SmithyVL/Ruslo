package ru.blimfy.server.usecase.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.server.db.entity.Channel

/**
 * Интерфейс для работы с каналами серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelService {
    /**
     * Возвращает новый [channel].
     */
    suspend fun createChannel(channel: Channel): Channel

    /**
     * Обновляет [newName] и [newNsfw] для канала с [id].
     */
    suspend fun modifyChannel(id: UUID, newName: String, newNsfw: Boolean): Channel

    /**
     * Возвращает существующий канал с [channelId].
     */
    suspend fun findChannel(channelId: UUID): Channel

    /**
     * Возвращает каналы сервера с [serverId].
     */
    fun findServerChannels(serverId: UUID): Flow<Channel>

    /**
     * Удаляет канал с таким [channelId] с сервера с [serverId].
     */
    suspend fun deleteChannel(channelId: UUID, serverId: UUID): Channel
}