package ru.blimfy.gateway.dto.user.friend

import java.util.UUID
import ru.blimfy.user.db.entity.Friend

/**
 * DTO с информацией о новом друге.
 *
 * @property friendId идентификатор друга.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewFriendDto(val friendId: UUID)

/**
 * Возвращает сущность друга из DTO представления для пользователя с [userId].
 */
fun NewFriendDto.toEntity(userId: UUID) = Friend(fromId = userId, toId = friendId)