package ru.blimfy.gateway.service.friend.request

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.user.friend.request.FriendRequestDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о запросах в друзья.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface FriendRequestControllerService {
    /**
     * Возвращает новый запрос в друзья с пользователем с [userId] для [user].
     */
    suspend fun createFriendRequest(userId: UUID, user: User): FriendRequestDto

    /**
     * Возвращает исходящие заявки в друзья для [user].
     */
    fun findOutgoingFriendRequests(user: User): Flow<FriendRequestDto>

    /**
     * Удаляет все исходящие заявки в друзья для [user].
     */
    suspend fun deleteAllOutgoingFriendRequests(user: User)

    /**
     * Удаляет исходящую от [user] заявку в друзья с пользователем с [userId].
     */
    suspend fun deleteOutgoingFriendRequest(userId: UUID, user: User)

    /**
     * Возвращает входящие заявки в друзья для [user].
     */
    fun findIncomingFriendRequests(user: User): Flow<FriendRequestDto>

    /**
     * Удаляет все входящие заявки в друзья для [user].
     */
    suspend fun deleteAllIncomingFriendRequests(user: User)

    /**
     * Удаляет входящую заявку в друзья от пользователя с [userId] с [user].
     */
    suspend fun deleteIncomingFriendRequest(userId: UUID, user: User)

    /**
     * Принимает запрос в друзья от пользователя с [userId] для [user].
     */
    suspend fun acceptFriendRequest(userId: UUID, user: User)
}