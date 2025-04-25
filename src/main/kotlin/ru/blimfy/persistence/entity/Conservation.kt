package ru.blimfy.persistence.entity

import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.direct.conservation.ConservationDto
import ru.blimfy.persistence.entity.base.BaseEntity

/**
 * Сущность с информацией о личном диалоге.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class Conservation : BaseEntity()

/**
 * Возвращает DTO представления сущности личного диалога.
 */
fun Conservation.toDto() = ConservationDto(id)