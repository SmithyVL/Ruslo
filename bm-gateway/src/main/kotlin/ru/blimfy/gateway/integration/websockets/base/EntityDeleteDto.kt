package ru.blimfy.gateway.integration.websockets.base

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.UUID

/**
 * DTO с полезной нагрузкой для события, вызванного удалением сущности, связанной с каналом или сервером.
 *
 * @property id идентификатор.
 * @property channelId идентификатор канала.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EntityDeleteDto(val id: UUID, val channelId: UUID, val serverId: UUID? = null)