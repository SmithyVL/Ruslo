package tech.ruslo.server.service.role

import kotlinx.coroutines.flow.Flow
import tech.ruslo.server.database.entity.Role

/**
 * Интерфейс для работы с ролями сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface RoleService {
    /**
     * Возвращает сохранённую [role].
     */
    suspend fun saveRole(role: Role): Role

    /**
     * Возвращает сохранённые [roles].
     */
    fun saveRoles(roles: List<Role>): Flow<Role>

    /**
     * Добавляет участника с [memberId] к роли с [roleId].
     */
    suspend fun addMember(roleId: Long, memberId: Long)
}