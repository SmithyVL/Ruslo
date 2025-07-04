package ru.blimfy.gateway.dto.websockets

import java.util.UUID

/**
 * DTO с полезной нагрузкой для события, вызванного закреплением/откреплением сообщения в канале.
 *
 * @property channelId идентификатор канала.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class PinsUpdateDto(val channelId: UUID, val serverId: UUID? = null)