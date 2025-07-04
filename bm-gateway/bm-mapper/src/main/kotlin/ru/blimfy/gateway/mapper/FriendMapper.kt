package ru.blimfy.gateway.mapper

import org.springframework.stereotype.Component
import ru.blimfy.domain.user.db.entity.Friend
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.dto.friend.FriendDto

/**
 * Маппер для превращения друга в DTO и обратно.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class FriendMapper(private val userService: UserService) {
    suspend fun toDto(friend: Friend) =
        FriendDto(friend.id, friend.fromId).apply { to = userService.findUser(friend.toId).toDto() }
}