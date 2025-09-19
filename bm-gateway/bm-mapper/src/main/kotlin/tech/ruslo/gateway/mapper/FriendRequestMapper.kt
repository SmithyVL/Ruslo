package tech.ruslo.gateway.mapper

import org.springframework.stereotype.Component
import tech.ruslo.domain.user.db.entity.FriendRequest
import tech.ruslo.domain.user.usecase.user.UserService
import tech.ruslo.gateway.dto.friend.request.FriendRequestDto

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