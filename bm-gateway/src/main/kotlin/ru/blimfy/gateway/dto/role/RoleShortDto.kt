package ru.blimfy.gateway.dto.role

import java.util.UUID
import ru.blimfy.server.db.entity.Role

/**
 * DTO с краткой информацией о роли сервера.
 *
 * @property id идентификатор.
 * @property name название.
 * @property color цвет.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class RoleShortDto(val id: UUID, val name: String, val color: String? = null)

/**
 * Возвращает DTO представления с краткой информацией из сущности роли.
 */
fun Role.toDto() = RoleShortDto(id, name, color)