package ru.blimfy.gateway.dto.user.friend

import java.util.UUID
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.user.db.entity.Friend

/**
 * DTO с информацией о друге.
 *
 * @property id идентификатор связи с другом.
 * @property fromId идентификатор пользователя, у которого есть друг.
 * @property to информация о пользователе, который является этим другом.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class FriendDto(val id: UUID, val fromId: UUID) {
    lateinit var to: UserDto
}

/**
 * Возвращает DTO представление из сущности друга.
 */
fun Friend.toDto() = FriendDto(id, fromId)