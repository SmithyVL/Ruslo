package ru.blimfy.user.usecase.friend

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.user.db.entity.Friend

/**
 * Интерфейс для работы с друзьями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface FriendService {
    /**
     * Возвращает нового [friend].
     */
    suspend fun createFriend(friend: Friend): Friend

    /**
     * Возвращает обновлённую информацию о пользователе с [toId] в друзьях с пользователем с [fromId] с новым [nick].
     */
    suspend fun changeFriendNick(toId: UUID, fromId: UUID, nick: String? = null): Friend

    /**
     * Возвращает друзей для пользователя с идентификатором [fromId].
     */
    fun findFriends(fromId: UUID): Flow<Friend>

    /**
     * Удаляет из друзей пользователя с [fromId] пользователя с [toId].
     */
    suspend fun deleteFriend(fromId: UUID, toId: UUID)
}