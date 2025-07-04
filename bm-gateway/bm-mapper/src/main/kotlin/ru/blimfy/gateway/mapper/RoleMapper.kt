package ru.blimfy.gateway.mapper

import ru.blimfy.gateway.dto.role.RoleDto
import ru.blimfy.domain.server.db.entity.Role

/**
 * Возвращает DTO представления с информацией из сущности роли.
 */
fun Role.toDto() = RoleDto(id, name, permissions, position, hoist, mentionable, color)