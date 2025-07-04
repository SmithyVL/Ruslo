package ru.blimfy.domain.server.api.dto.role

/**
 * DTO с обновлённой информацией о роли сервера.
 *
 * @property hoist если роль закреплена в списке участников канала сервера.
 * @property mentionable является ли роль упоминаемой.
 * @property name название.
 * @property permissions битовая маска с разрешениями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyRoleDto(
    val color: String? = null,
    val hoist: Boolean,
    val mentionable: Boolean,
    val name: String,
    val permissions: String,
)