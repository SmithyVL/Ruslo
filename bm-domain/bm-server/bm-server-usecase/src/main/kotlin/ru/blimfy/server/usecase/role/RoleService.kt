package ru.blimfy.server.usecase.role

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import ru.blimfy.server.db.entity.Role

/**
 * Интерфейс для работы с ролями сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface RoleService {
    /**
     * Возвращает новую [role].
     */
    suspend fun createRole(role: Role): Role

    /**
     * Возвращает обновлённую [role].
     */
    suspend fun modifyRole(role: Role): Role

    /**
     * Возвращает дефолтную роль сервера с идентификатором [serverId].
     */
    suspend fun findDefaultServerRole(serverId: UUID): Role

    /**
     * Возвращает роль c [id] для сервера с [serverId].
     */
    suspend fun findServerRole(id: UUID, serverId: UUID): Role

    /**
     * Возвращает роль с идентификатором [id].
     */
    suspend fun findRole(id: UUID): Role

    /**
     * Возвращает роли сервера с [serverId].
     */
    fun findServerRoles(serverId: UUID): Flow<Role>
}