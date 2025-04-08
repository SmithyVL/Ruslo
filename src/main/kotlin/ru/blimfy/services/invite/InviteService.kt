package ru.blimfy.services.invite

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.persistence.entity.Invite

/**
 * Интерфейс для работы с приглашениями на сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface InviteService {
    /**
     * Возвращает новый или обновлённый [invite].
     */
    suspend fun saveInvite(invite: Invite): Invite

    /**
     * Возвращает приглашения на сервер с идентификатором [serverId].
     */
    suspend fun findServerInvites(serverId: UUID): Flow<Invite>

    /**
     * Удаляет приглашение с таким [id].
     */
    suspend fun deleteInvite(id: UUID)
}