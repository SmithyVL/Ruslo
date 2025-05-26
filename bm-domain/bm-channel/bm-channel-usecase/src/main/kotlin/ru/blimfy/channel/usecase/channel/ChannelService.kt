package ru.blimfy.channel.usecase.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.channel.db.entity.Channel

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
    suspend fun save(channel: Channel): Channel

    /**
     * Возвращает канал с [id] и новыми [recipients].
     */
    suspend fun addRecipients(id: UUID, recipients: Set<UUID>): Channel

    /**
     * Возвращает канал с [id] и удалённым [recipient].
     */
    suspend fun deleteRecipient(id: UUID, recipient: UUID): Channel

    /**
     * Возвращает группу с [id] и [newOwnerId].
     */
    suspend fun changeOwner(id: UUID, newOwnerId: UUID): Channel

    /**
     * Возвращает канал с [id].
     */
    suspend fun findChannel(id: UUID): Channel

    /**
     * Возвращает личный диалог для [recipients], если такой есть.
     */
    suspend fun findDm(recipients: Set<UUID>): Channel?

    /**
     * Возвращает личные каналы для [userId].
     */
    fun findDmChannels(userId: UUID): Flow<Channel>

    /**
     * Возвращает каналы сервера с [serverId].
     */
    fun findServerChannels(serverId: UUID): Flow<Channel>

    /**
     * Удаляет канал с [id].
     */
    suspend fun deleteChannel(id: UUID): Channel

    /**
     * Проверяет разрешение пользователя с [userId] на просмотр канала с [id].
     */
    suspend fun checkChannelViewAccess(id: UUID, userId: UUID)

    /**
     * Проверяет разрешение пользователя с [userId] на изменение группы с [id].
     */
    suspend fun checkGroupDmWriteAccess(id: UUID, userId: UUID)
}