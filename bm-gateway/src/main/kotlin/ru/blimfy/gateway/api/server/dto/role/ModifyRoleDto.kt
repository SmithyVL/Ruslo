package ru.blimfy.gateway.api.server.dto.role

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import ru.blimfy.server.db.entity.Role

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
@JsonInclude(NON_NULL)
data class ModifyRoleDto(
    val color: String?,
    val hoist: Boolean,
    val mentionable: Boolean,
    val name: String,
    val permissions: String,
)

/**
 * Возвращает сущность [role] из DTO представления с обновлённой информацией.
 */
fun ModifyRoleDto.toEntity(role: Role) = Role(role.serverId).apply {
    name = this@toEntity.name
    permissions = this@toEntity.permissions
    color = this@toEntity.color
    mentionable = this@toEntity.mentionable
    position = role.position
    createdDate = role.createdDate
}