package tech.ruslo.gateway.mapper

import tech.ruslo.gateway.dto.role.RoleDto
import tech.ruslo.domain.server.db.entity.Role

/**
 * Возвращает DTO представления с информацией из сущности роли.
 */
fun Role.toDto() = RoleDto(id, name, permissions, position, hoist, mentionable, color)