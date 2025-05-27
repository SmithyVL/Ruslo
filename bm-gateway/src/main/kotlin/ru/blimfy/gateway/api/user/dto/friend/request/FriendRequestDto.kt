package ru.blimfy.gateway.api.user.dto.friend.request

import java.util.UUID
import ru.blimfy.gateway.api.dto.UserDto
import ru.blimfy.user.db.entity.FriendRequest

/**
 * DTO с информацией о запросе в друзья.
 *
 * @property id идентификатор запроса.
 * @property from информация о пользователе, приглашающего в друзья.
 * @property to информация о пользователе, который может стать другом.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class FriendRequestDto(val id: UUID) {
    lateinit var from: UserDto
    lateinit var to: UserDto
}

/**
 * Возвращает DTO представление из сущности запроса в друзья.
 */
fun FriendRequest.toDto() = FriendRequestDto(id)