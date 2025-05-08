package ru.blimfy.gateway.dto

import java.util.UUID
import ru.blimfy.user.db.entity.User

/**
 * DTO с информацией о пользователе.
 *
 * @property id идентификатор.
 * @property username логин.
 * @property avatarUrl ссылка на файл аватарки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class UserDto(val id: UUID, val username: String, val avatarUrl: String? = null)

/**
 * Возвращает DTO представление с информацией из сущности пользователя.
 */
fun User.toDto() = UserDto(id, username, avatarUrl)