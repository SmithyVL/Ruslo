package ru.blimfy.gateway.dto.direct.conservation

import java.util.UUID
import ru.blimfy.direct.db.entity.Conservation

/**
 * DTO с информацией о личном диалоге.
 *
 * @property id идентификатор.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ConservationDto(val id: UUID)

/**
 * Возвращает DTO представления сущности личного диалога.
 */
fun Conservation.toDto() = ConservationDto(id)