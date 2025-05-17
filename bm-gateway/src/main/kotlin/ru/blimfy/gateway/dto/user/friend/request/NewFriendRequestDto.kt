package ru.blimfy.gateway.dto.user.friend.request

import java.util.UUID
import ru.blimfy.user.db.entity.FriendRequest

/**
 * DTO с информацией о новом запросе в друзья.
 *
 * @property recipientId идентификатор будущего друга.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewFriendRequestDto(val recipientId: UUID)

/**
 * Возвращает сущность запроса в друзья из DTO представления для пользователя с [fromId].
 */
fun NewFriendRequestDto.toEntity(fromId: UUID) = FriendRequest(fromId = fromId, toId = recipientId)