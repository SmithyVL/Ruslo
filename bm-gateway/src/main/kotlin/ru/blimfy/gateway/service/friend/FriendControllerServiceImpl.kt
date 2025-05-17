package ru.blimfy.gateway.service.friend

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.user.friend.FriendNickDto
import ru.blimfy.gateway.dto.user.friend.toDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.user.db.entity.Friend
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.friend.FriendService
import ru.blimfy.user.usecase.user.UserService

/**
 * Реализация интерфейса для работы с обработкой запросов о друзьях.
 *
 * @property friendService сервис для работы с друзьями.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class FriendControllerServiceImpl(
    private val friendService: FriendService,
    private val userService: UserService,
) : FriendControllerService {
    override suspend fun changeFriendNick(friendId: UUID, nickInfo: FriendNickDto, currentUser: User) =
        friendService.changeFriendNick(friendId, nickInfo.nick, currentUser.id)
            .toDtoWithUserInfo()

    override fun findFriends(currentUser: User) =
        friendService.findFriends(currentUser.id)
            .map { it.toDtoWithUserInfo() }

    override suspend fun deleteFriend(id: UUID) = friendService.deleteFriend(id)

    /**
     * Возвращает DTO представление друга с информацией о пользователе, являющимся другом.
     */
    private suspend fun Friend.toDtoWithUserInfo() =
        this.toDto().apply { to = userService.findUser(toId).toDto() }
}