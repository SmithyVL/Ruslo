package ru.blimfy.gateway.service.friend

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.user.friend.toDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.user.db.entity.Friend
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.friend.FriendService
import ru.blimfy.user.usecase.user.UserService

/**
 * Реализация интерфейса для работы с обработкой друзей.
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
    override fun findUserFriends(user: User) =
        friendService.findFriends(user.id).map { it.toDtoWithData() }

    override suspend fun deleteFriend(userId: UUID, user: User) =
        friendService.deleteFriend(user.id, userId)

    override suspend fun changeFriendNick(userId: UUID, nick: String?, user: User) =
        friendService.changeFriendNick(userId, user.id, nick).toDtoWithData()

    /**
     * Возвращает DTO представление друга.
     */
    private suspend fun Friend.toDtoWithData() =
        this.toDto().apply { to = userService.findUser(toId).toDto() }
}