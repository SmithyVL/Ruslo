package tech.ruslo.domain.user.usecase.friend.request

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import tech.ruslo.domain.user.db.entity.FriendRequest

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
     * Удаляет заявку в друзья от пользователя с [fromId] с пользователем с [toId].
     */
    suspend fun deleteFriendRequest(fromId: UUID, toId: UUID)
}