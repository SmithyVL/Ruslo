package ru.blimfy.gateway.service.friend.request

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.user.friend.request.FriendRequestDto
import ru.blimfy.gateway.dto.user.friend.request.NewFriendRequestDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о запросах в друзья.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface FriendRequestControllerService {
    /**
     * Возвращает новый [newFriendRequest] для [currentUser].
     */
    suspend fun createFriendRequest(newFriendRequest: NewFriendRequestDto, currentUser: User): FriendRequestDto

    /**
     * Возвращает исходящие заявки в друзья для [currentUser].
     */
    fun findOutgoingFriendRequests(currentUser: User): Flow<FriendRequestDto>

    /**
     * Возвращает входящие заявки в друзья для [currentUser].
     */
    fun findIncomingFriendRequests(currentUser: User): Flow<FriendRequestDto>

    /**
     * Удаляет все исходящие заявки в друзья для [currentUser].
     */
    suspend fun deleteAllOutgoingFriendRequests(currentUser: User)

    /**
     * Удаляет все входящие заявки в друзья для [currentUser].
     */
    suspend fun deleteAllIncomingFriendRequests(currentUser: User)

    /**
     * Удаляет исходящую от [currentUser] заявку в друзья с [friendRequestId].
     */
    suspend fun deleteOutgoingFriendRequest(friendRequestId: UUID, currentUser: User)

    /**
     * Удаляет входящую заявку в друзья с идентификатором [friendRequestId] с [currentUser].
     */
    suspend fun deleteIncomingFriendRequest(friendRequestId: UUID, currentUser: User)

    /**
     * Принимает запрос в друзья с [friendRequestId] для [currentUser].
     */
    suspend fun acceptFriendRequest(friendRequestId: UUID, currentUser: User)
}