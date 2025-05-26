package ru.blimfy.user.usecase.friend.request

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.user.db.entity.FriendRequest
import ru.blimfy.user.db.repository.FriendRequestRepository
import ru.blimfy.user.usecase.exception.UserErrors.FRIEND_REQUEST_NOT_FOUND

/**
 * Реализация интерфейса для работы с запросами в друзья.
 *
 * @property friendRequestRepo репозиторий для работы с заявками в друзья.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class FriendRequestServiceImpl(private val friendRequestRepo: FriendRequestRepository) : FriendRequestService {
    override suspend fun createFriendRequest(friendRequest: FriendRequest) =
        friendRequestRepo.save(friendRequest)

    override suspend fun findFriendRequest(id: UUID) =
        friendRequestRepo.findById(id) ?: throw NotFoundException(FRIEND_REQUEST_NOT_FOUND.msg)

    override fun findOutgoingFriendRequests(userId: UUID) =
        friendRequestRepo.findAllByFromId(userId)

    override fun findIncomingFriendRequests(userId: UUID) =
        friendRequestRepo.findAllByToId(userId)

    override suspend fun deleteAllOutgoingFriendRequests(userId: UUID) =
        friendRequestRepo.deleteAllByFromId(userId)

    override suspend fun deleteAllIncomingFriendRequests(userId: UUID) =
        friendRequestRepo.deleteAllByToId(userId)

    override suspend fun deleteFriendRequest(fromId: UUID, toId: UUID) =
        friendRequestRepo.deleteByFromIdAndToId(fromId = fromId, toId = toId)
}