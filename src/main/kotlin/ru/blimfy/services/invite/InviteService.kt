package ru.blimfy.services.invite

import java.util.UUID
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
     * Возвращает существующее приглашение с [id].
     */
    suspend fun findInvite(id: UUID): Invite

    /**
     * Удаляет приглашение с таким [id].
     */
    suspend fun deleteInvite(id: UUID)
}