package ru.blimfy.gateway.api.mapper

import org.springframework.stereotype.Component
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.user.dto.friend.request.FriendRequestDto
import ru.blimfy.user.db.entity.FriendRequest
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения запроса в друзья в DTO и обратно.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class FriendRequestMapper(private val userService: UserService) {
    suspend fun toDto(friendRequest: FriendRequest) =
        FriendRequestDto(friendRequest.id).apply {
            from = userService.findUser(friendRequest.fromId).toDto()
            to = userService.findUser(friendRequest.toId).toDto()
        }
}