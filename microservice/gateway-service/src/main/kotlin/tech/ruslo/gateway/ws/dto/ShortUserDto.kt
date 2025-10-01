package tech.ruslo.gateway.ws.dto

/**
 * DTO с краткой информацией о пользователе.
 *
 * @property id идентификатор пользователя.
 * @property username логин пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ShortUserDto(val id: Long, val username: String)