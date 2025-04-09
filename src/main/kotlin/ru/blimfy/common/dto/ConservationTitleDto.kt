package ru.blimfy.common.dto

import java.util.UUID

/**
 * DTO с информацией о заголовке личного диалога.
 *
 * @property id идентификатор.
 * @property name название личного диалога равное имени собеседника.
 * @property iconUrl ссылка на иконку личного диалога равной ссылке на аватарку собеседника.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ConservationTitleDto(val id: UUID, val name: String, val iconUrl: String? = null)
