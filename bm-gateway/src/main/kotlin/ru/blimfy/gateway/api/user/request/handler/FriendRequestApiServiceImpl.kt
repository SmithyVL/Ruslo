package ru.blimfy.gateway.api.user.request.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.gateway.api.mapper.FriendRequestMapper
import ru.blimfy.gateway.api.user.dto.friend.request.FriendRequestDto
import ru.blimfy.user.db.entity.Friend
import ru.blimfy.user.db.entity.FriendRequest
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.friend.FriendService
import ru.blimfy.user.usecase.friend.request.FriendRequestService
import ru.blimfy.user.usecase.user.UserService

/**
 * Реализация интерфейса для работы с обработкой запросов о запросах в друзья.
 *
 * @property friendRequestService сервис для работы с заявками в друзья.
 * @property friendService сервис для работы с друзьями.
 * @property userService сервис для работы с пользователями.
 * @property friendRequestMapper маппер для работы с запроса в друзья.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class FriendRequestApiServiceImpl(
    private val friendRequestService: FriendRequestService,
    private val friendService: FriendService,
    private val userService: UserService,
    private val friendRequestMapper: FriendRequestMapper,
) : FriendRequestApiService {
    override suspend fun createFriendRequest(username: String, user: User): FriendRequestDto {
        val toUserId = userService.findUser(username).id
        return friendRequestService.createFriendRequest(FriendRequest(fromId = user.id, toId = toUserId))
            .let { friendRequestMapper.toDto(it) }
    }

    override fun findOutgoingFriendRequests(user: User) =
        friendRequestService.findOutgoingFriendRequests(user.id)
            .map { friendRequestMapper.toDto(it) }

    override suspend fun deleteAllOutgoingFriendRequests(user: User) =
        friendRequestService.deleteAllOutgoingFriendRequests(user.id)

    override suspend fun deleteOutgoingFriendRequest(userId: UUID, user: User) =
        friendRequestService.deleteFriendRequest(fromId = user.id, toId = userId)

    override fun findIncomingFriendRequests(user: User) =
        friendRequestService.findIncomingFriendRequests(user.id)
            .map { friendRequestMapper.toDto(it) }

    override suspend fun deleteAllIncomingFriendRequests(user: User) =
        friendRequestService.deleteAllIncomingFriendRequests(user.id)

    override suspend fun deleteIncomingFriendRequest(userId: UUID, user: User) =
        friendRequestService.deleteFriendRequest(fromId = userId, toId = user.id)

    override suspend fun acceptFriendRequest(userId: UUID, user: User) {
        // Создаём связь с другом для создателя заявки в друзья.
        friendService.createFriend(Friend(fromId = userId, toId = user.id))

        // Создаём связь с другом для приглашённого в друзья.
        friendService.createFriend(Friend(fromId = user.id, toId = userId))

        // Удаляем уже не нужную заявку в друзья.
        friendRequestService.deleteFriendRequest(fromId = userId, toId = user.id)
    }
}