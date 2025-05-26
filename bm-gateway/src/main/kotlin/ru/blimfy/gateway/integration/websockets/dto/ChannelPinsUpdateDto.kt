package ru.blimfy.gateway.integration.websockets.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.util.UUID

/**
 * DTO с полезной нагрузкой для события, вызванного закреплением/откреплением сообщения в канале.
 *
 * @property channelId идентификатор канала.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class ChannelPinsUpdateDto(val channelId: UUID, val serverId: UUID? = null)