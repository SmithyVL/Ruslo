package tech.ruslo.server.dto.role

import java.util.UUID

/**
 * DTO с информацией о позиции роли сервера.
 *
 * @property id идентификатор.
 * @property position позиция.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class RolePositionDto(val id: UUID, val position: Int)