package tech.ruslo.domain.channel.api.dto.channel

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
data class ChannelPositionDto(val id: UUID, val position: Long = 0, val parentId: UUID? = null)