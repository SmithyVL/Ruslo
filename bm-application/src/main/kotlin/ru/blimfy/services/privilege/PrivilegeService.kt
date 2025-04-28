package ru.blimfy.services.privilege

import java.util.UUID

/**
 * Интерфейс для работы с привилегиями ролей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface PrivilegeService {
    /**
     * Создаёт стандартные привилегии для роли с идентификатором [roleId].
     */
    suspend fun initDefaultPrivileges(roleId: UUID)
}