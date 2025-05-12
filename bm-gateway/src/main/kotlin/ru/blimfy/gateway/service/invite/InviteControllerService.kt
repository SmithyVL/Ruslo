package ru.blimfy.gateway.service.invite

import java.util.UUID
import ru.blimfy.gateway.dto.invite.InviteDetailsDto
import ru.blimfy.gateway.dto.invite.InviteDto
import ru.blimfy.gateway.dto.invite.NewInviteDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Интерфейс для работы с обработкой запросов о приглашениях серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface InviteControllerService {
    /**
     * Возвращает новый [newInviteDto], который создаёт [user].
     */
    suspend fun createInvite(newInviteDto: NewInviteDto, user: CustomUserDetails): InviteDto

    /**
     * Возвращает приглашение с [inviteId] на сервер.
     */
    suspend fun findInvite(inviteId: UUID): InviteDetailsDto

    /**
     * Удаляет приглашение с [inviteId] на сервер пользователем [user].
     */
    suspend fun deleteInvite(inviteId: UUID, user: CustomUserDetails)

    /**
     * Применяет приглашение с [inviteId] для пользователя [user].
     */
    suspend fun useInvite(inviteId: UUID, user: CustomUserDetails)
}