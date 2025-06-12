package ru.blimfy.gateway.api.server.dto.role

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import ru.blimfy.server.db.entity.Role
import java.util.*

/**
 * DTO с информацией о новой роли сервера.
 *
 * @property name название.
 * @property permissions битовая маска с разрешениями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class NewRoleDto(val name: String, val permissions: String)

/**
 * Возвращает сущность из DTO представления новой роли.
 */
fun NewRoleDto.toEntity(serverId: UUID) = Role(serverId).apply {
    name = this@toEntity.name
    permissions = this@toEntity.permissions
}
