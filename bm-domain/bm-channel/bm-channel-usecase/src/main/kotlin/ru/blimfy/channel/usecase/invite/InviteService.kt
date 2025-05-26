package ru.blimfy.channel.usecase.invite

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.common.enumeration.InviteTypes

/**
 * Интерфейс для работы с приглашениями каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface InviteService {
    /**
     * Возвращает сохранённое [invite].
     */
    suspend fun saveInvite(invite: Invite): Invite

    /**
     * Возвращает приглашение с [id].
     */
    suspend fun findInvite(id: UUID): Invite

    /**
     * Возвращает [type] приглашения для [parentId].
     */
    fun findInvites(parentId: UUID, type: InviteTypes? = null): Flow<Invite>

    /**
     * Возвращает удаляемое приглашение с [id].
     */
    suspend fun deleteInvite(id: UUID): Invite
}