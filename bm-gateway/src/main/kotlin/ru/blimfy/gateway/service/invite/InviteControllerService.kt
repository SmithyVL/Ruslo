package ru.blimfy.gateway.service.invite

import java.util.UUID
import ru.blimfy.gateway.dto.server.invite.InviteDetailsDto
import ru.blimfy.gateway.dto.server.invite.InviteDto
import ru.blimfy.gateway.dto.server.invite.NewInviteDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о приглашениях серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface InviteControllerService {
    /**
     * Возвращает новый [newInviteDto], который создаёт [currentUser].
     */
    suspend fun createInvite(newInviteDto: NewInviteDto, currentUser: User): InviteDto

    /**
     * Возвращает приглашение с [inviteId] на сервер.
     */
    suspend fun findInvite(inviteId: UUID): InviteDetailsDto

    /**
     * Удаляет приглашение с [inviteId] на сервер пользователем [currentUser].
     */
    suspend fun deleteInvite(inviteId: UUID, currentUser: User)

    /**
     * Применяет приглашение с [inviteId] для пользователя [currentUser].
     */
    suspend fun useInvite(inviteId: UUID, currentUser: User)
}