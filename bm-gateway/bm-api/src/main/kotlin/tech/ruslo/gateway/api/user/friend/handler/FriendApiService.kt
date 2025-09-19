package tech.ruslo.gateway.api.user.friend.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.dto.friend.FriendDto

/**
 * Интерфейс для работы с обработкой запросов о друзьях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface FriendApiService {
    /**
     * Возвращает друзей [user].
     */
    fun findUserFriends(user: User): Flow<FriendDto>

    /**
     * Возвращает друга с новым [nick] пользователя с [userId] для [user].
     */
    suspend fun changeFriendNick(userId: UUID, nick: String? = null, user: User): FriendDto

    /**
     * Удаляет из друзей [user] пользователя с [userId].
     */
    suspend fun deleteFriend(userId: UUID, user: User)
}