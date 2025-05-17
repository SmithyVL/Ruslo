package ru.blimfy.gateway.dto.server.role

import java.util.UUID
import ru.blimfy.server.db.entity.Role

/**
 * DTO с информацией о роли сервера.
 *
 * @property id идентификатор.
 * @property name название.
 * @property permissions битовая маска с разрешениями.
 * @property position номер в сортировке ролей внутри сервера.
 * @property hoist если роль закреплена в списке участников канала сервера.
 * @property mentionable является ли роль упоминаемой.
 * @property color цвет роли.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class RoleDto(
    val id: UUID,
    val name: String,
    val permissions: String,
    val position: Int,
    val hoist: Boolean,
    val mentionable: Boolean,
    val color: String? = null,
)

/**
 * Возвращает DTO представления с информацией из сущности роли.
 */
fun Role.toDto() = RoleDto(id, name, permissions, position, hoist, mentionable, color)