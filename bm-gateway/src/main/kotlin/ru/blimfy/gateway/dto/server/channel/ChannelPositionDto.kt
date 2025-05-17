package ru.blimfy.gateway.dto.server.channel

import jakarta.validation.constraints.PositiveOrZero
import java.util.UUID

/**
 * DTO с информацией для обновления позиции канала сервера.
 *
 * @property id идентификатор.
 * @property position новый номер канала сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ChannelPositionDto(
    val id: UUID,
    @PositiveOrZero(message = "Channel position must be positive or zero") val position: Int,
)