package ru.blimfy.gateway.api.server.role.handler

import ru.blimfy.gateway.api.dto.RoleDto
import ru.blimfy.gateway.api.server.dto.role.ModifyRoleDto
import ru.blimfy.gateway.api.server.dto.role.NewRoleDto
import ru.blimfy.user.db.entity.User
import java.util.*

/**
 * Интерфейс для работы с обработкой запросов о ролях серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface RoleApiService {
    /**
     * Возвращает роль, созданную по информации из [newRoleDto] для сервера с [serverId], которую создаёт [user].
     */
    suspend fun createRole(serverId: UUID, newRoleDto: NewRoleDto, user: User): RoleDto

    /**
     * Возвращает роль с [id], обновлённую по информации из [modifyRoleDto] для сервера с [serverId], которую обновляет
     * [user].
     */
    suspend fun modifyRole(id: UUID, serverId: UUID, modifyRoleDto: ModifyRoleDto, user: User): RoleDto
}