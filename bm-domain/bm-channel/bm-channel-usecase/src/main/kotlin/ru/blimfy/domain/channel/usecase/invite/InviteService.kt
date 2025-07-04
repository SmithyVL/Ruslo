package ru.blimfy.domain.channel.usecase.invite

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.domain.channel.db.entity.Invite

/**
 * Интерфейс для работы с приглашениями каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface InviteService {
    /**
     * Возвращает новое [invite].
     */
    suspend fun createInvite(invite: Invite): Invite

    /**
     * Возвращает приглашение с [id].
     */
    suspend fun findInvite(id: UUID): Invite

    /**
     * Возвращает приглашения для сервера с [serverId].
     */
    fun findServerInvites(serverId: UUID): Flow<Invite>

    /**
     * Возвращает приглашения для канала с [channelId].
     */
    fun findChannelInvites(channelId: UUID): Flow<Invite>

    /**
     * Удаляет приглашение с [id].
     */
    suspend fun deleteInvite(id: UUID)
}