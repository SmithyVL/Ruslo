package ru.blimfy.gateway.api.channel.invite.handler

import java.util.UUID
import ru.blimfy.gateway.api.dto.InviteDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о приглашениях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface InviteApiService {
    /**
     * Возвращает приглашение с [id].
     */
    suspend fun findInvite(id: UUID): InviteDto

    /**
     * Возвращает удалённое [user] приглашение с [id].
     */
    suspend fun deleteInvite(id: UUID, user: User): InviteDto

    /**
     * Использует приглашение с [id] для [user].
     */
    suspend fun useInvite(id: UUID, user: User)
}