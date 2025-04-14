package ru.blimfy.services.role

import java.util.UUID
import ru.blimfy.persistence.entity.Role

/**
 * Интерфейс для работы с ролями сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface RoleService {
    /**
     * Возвращает новую или обновлённую [role]. Для новой роли создаётся стандартный набор привилегий.
     */
    suspend fun saveRole(role: Role): Role

    /**
     * Возвращает дефолтную роль сервера с идентификатором [serverId].
     */
    suspend fun findDefaultServerRole(serverId: UUID): Role
}