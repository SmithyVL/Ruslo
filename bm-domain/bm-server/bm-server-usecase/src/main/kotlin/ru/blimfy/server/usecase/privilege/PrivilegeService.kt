package ru.blimfy.server.usecase.privilege

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.server.db.entity.Privilege

/**
 * Интерфейс для работы с привилегиями ролей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface PrivilegeService {
    /**
     * Возвращает новую [privilege].
     */
    suspend fun savePrivilege(privilege: Privilege): Privilege

    /**
     * Возвращает привилегии роли с идентификатором [roleId].
     */
    fun findRolePrivileges(roleId: UUID): Flow<Privilege>
}