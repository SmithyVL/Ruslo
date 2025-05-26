package ru.blimfy.gateway.service.friend

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.user.friend.FriendDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о друзьях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface FriendControllerService {
    /**
     * Возвращает друзей [user].
     */
    fun findUserFriends(user: User): Flow<FriendDto>

    /**
     * Удаляет из друзей [user] пользователя с [userId].
     */
    suspend fun deleteFriend(userId: UUID, user: User)

    /**
     * Возвращает друга с новым [nick] пользователя с [userId] для [user].
     */
    suspend fun changeFriendNick(userId: UUID, nick: String? = null, user: User): FriendDto
}