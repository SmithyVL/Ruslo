package ru.blimfy.gateway.api.channel.invite.handler

import java.util.UUID
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.dto.invite.InviteDto

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
     * [user] удаляет приглашение с [id].
     */
    suspend fun deleteInvite(id: UUID, user: User)

    /**
     * Использует приглашение с [id] для [user].
     */
    suspend fun useInvite(id: UUID, user: User)
}