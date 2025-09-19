package tech.ruslo.gateway.dto.websockets.delete

import java.util.UUID

/**
 * DTO с полезной нагрузкой для события, вызванного удалением сообщения.
 *
 * @property id идентификатор сообщения.
 * @property channelId идентификатор канала.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessageDeleteDto(val id: UUID, val channelId: UUID, val serverId: UUID? = null)