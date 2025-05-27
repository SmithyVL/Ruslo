package ru.blimfy.gateway.api.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
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
@JsonInclude(NON_NULL)
data class RoleDto(
    val id: UUID,
    val name: String,
    val permissions: String = "0",
    val position: Int = 0,
    val hoist: Boolean = false,
    val mentionable: Boolean = false,
    val color: String? = null,
)

/**
 * Возвращает DTO представления с информацией из сущности роли.
 */
fun Role.toDto() = RoleDto(id, name, permissions, position, hoist, mentionable, color)