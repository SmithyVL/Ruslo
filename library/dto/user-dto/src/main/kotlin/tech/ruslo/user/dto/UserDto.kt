package tech.ruslo.user.dto

import java.time.Instant

/**
 * DTO с информацией о пользователе.
 *
 * @property id идентификатор.
 * @property username логин.
 * @property passwordHash хэш пароля.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class UserDto(
    val id: Long? = null,
    val username: String,
    val passwordHash: String,
    val createdDate: Instant? = null,
)