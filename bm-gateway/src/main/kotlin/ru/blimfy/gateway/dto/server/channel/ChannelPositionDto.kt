package ru.blimfy.gateway.dto.server.channel

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

/**
 * DTO с информацией о новой позиции канала сервера.
 *
 * @property id идентификатор.
 * @property position новая позиция.
 * @property parentId идентификатор родительского канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Информация о новой позиции канала сервера")
data class ChannelPositionDto(val id: UUID, val position: Long, val parentId: UUID? = null)