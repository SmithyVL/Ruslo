package ru.blimfy.gateway.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.user.db.entity.User

/**
 * DTO с информацией о пользователе.
 *
 * @property id идентификатор.
 * @property username логин.
 * @property email электронная почта.
 * @property createdDate дата создания в UTC.
 * @property avatarUrl ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class UserDto(
    val id: UUID,
    val username: String,
    val email: String,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC") val createdDate: Instant,
    val avatarUrl: String? = null
)

/**
 * Возвращает DTO представление сущности пользователя.
 */
fun User.toDto() = UserDto(id, username, email, createdDate, avatarUrl)