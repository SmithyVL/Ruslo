package ru.blimfy.user.usecase.friend.request

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.user.db.entity.FriendRequest

/**
 * Интерфейс для работы с запросами в друзья.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface FriendRequestService {
    /**
     * Возвращает новую [friendRequest].
     */
    suspend fun createFriendRequest(friendRequest: FriendRequest): FriendRequest

    /**
     * Возвращает заявку в друзья с [id].
     */
    suspend fun findFriendRequest(id: UUID): FriendRequest

    /**
     * Возвращает исходящие заявки в друзья для пользователя с [userId].
     */
    fun findOutgoingFriendRequests(userId: UUID): Flow<FriendRequest>

    /**
     * Возвращает входящие заявки в друзья для пользователя с [userId].
     */
    fun findIncomingFriendRequests(userId: UUID): Flow<FriendRequest>

    /**
     * Удаляет все исходящие заявки в друзья для пользователя с [userId].
     */
    suspend fun deleteAllOutgoingFriendRequests(userId: UUID)

    /**
     * Удаляет все входящие заявки в друзья для пользователя с [userId].
     */
    suspend fun deleteAllIncomingFriendRequests(userId: UUID)

    /**
     * Удаляет исходящую заявку в друзья с [id] от [fromId].
     */
    suspend fun deleteOutgoingFriendRequest(id: UUID, fromId: UUID)

    /**
     * Удаляет входящую заявку в друзья с [id] к [toId].
     */
    suspend fun deleteIncomingFriendRequest(id: UUID, toId: UUID)
}