package tech.ruslo.gateway.dto.websockets.delete

import java.util.UUID

/**
 * DTO с полезной нагрузкой для события, вызванного удалением роли сервера.
 *
 * @property serverId идентификатор сервера.
 * @property roleId идентификатор роли.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class RoleDeleteDto(val serverId: UUID, val roleId: UUID)