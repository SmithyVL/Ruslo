package ru.blimfy.gateway.service.friend.request

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.user.friend.request.NewFriendRequestDto
import ru.blimfy.gateway.dto.user.friend.request.toDto
import ru.blimfy.gateway.dto.user.friend.request.toEntity
import ru.blimfy.gateway.dto.user.toDto
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
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class FriendRequestControllerServiceImpl(
    private val friendRequestService: FriendRequestService,
    private val friendService: FriendService,
    private val userService: UserService,
) : FriendRequestControllerService {
    override suspend fun createFriendRequest(newFriendRequest: NewFriendRequestDto, currentUser: User) =
        friendRequestService.createFriendRequest(newFriendRequest.toEntity(currentUser.id)).toDtoWithUsersInfos()

    override fun findOutgoingFriendRequests(currentUser: User) =
        friendRequestService.findOutgoingFriendRequests(currentUser.id).map { it.toDtoWithUsersInfos() }

    override fun findIncomingFriendRequests(currentUser: User) =
        friendRequestService.findIncomingFriendRequests(currentUser.id).map { it.toDtoWithUsersInfos() }

    override suspend fun deleteAllOutgoingFriendRequests(currentUser: User) =
        friendRequestService.deleteAllOutgoingFriendRequests(currentUser.id)

    override suspend fun deleteAllIncomingFriendRequests(currentUser: User) =
        friendRequestService.deleteAllIncomingFriendRequests(currentUser.id)

    override suspend fun deleteOutgoingFriendRequest(friendRequestId: UUID, currentUser: User) =
        friendRequestService.deleteOutgoingFriendRequest(id = friendRequestId, fromId = currentUser.id)

    override suspend fun deleteIncomingFriendRequest(friendRequestId: UUID, currentUser: User) =
        friendRequestService.deleteIncomingFriendRequest(id = friendRequestId, toId = currentUser.id)

    override suspend fun acceptFriendRequest(friendRequestId: UUID, currentUser: User) {
        // Ищем заявку в друзья, чтобы на её основе создать две связи в друзья.
        val friendRequest = friendRequestService.findFriendRequest(friendRequestId)

        // Создаём связь с другом для создателя заявки в друзья.
        friendService.createFriend(Friend(fromId = friendRequest.fromId, toId = currentUser.id))

        // Создаём связь с другом для приглашённого в друзья.
        friendService.createFriend(Friend(fromId = currentUser.id, toId = friendRequest.fromId))

        // Удаляем уже не нужную заявку в друзья
        friendRequestService.deleteIncomingFriendRequest(id = friendRequestId, toId = currentUser.id)
    }

    /**
     * Возвращает DTO представление запроса в друзья с информацией о пользователях, которые планируют стать друзьями.
     */
    private suspend fun FriendRequest.toDtoWithUsersInfos() =
        this.toDto().apply {
            from = userService.findUser(fromId).toDto()
            to = userService.findUser(toId).toDto()
        }
}