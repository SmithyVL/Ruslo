package ru.blimfy.gateway.dto.member

/**
 * DTO с информацией о новом нике.
 *
 * @property nick новый ник.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewNickDto(val nick: String? = null)