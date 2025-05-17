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
     * Изменяет ник друга с [id] на [newNick] и возвращает информацию о нём для пользователя с идентификатором [userId].
     */
    suspend fun changeFriendNick(id: UUID, newNick: String, userId: UUID): Friend

    /**
     * Возвращает информацию о друге между пользователем с идентификатором [fromId] и пользователем с идентификатором
     * [toId].
     */
    suspend fun findFriend(fromId: UUID, toId: UUID): Friend?

    /**
     * Возвращает друзей для пользователя с идентификатором [fromId].
     */
    fun findFriends(fromId: UUID): Flow<Friend>

    /**
     * Удаляет информацию о друге на основе связи друга с [id].
     */
    suspend fun deleteFriend(id: UUID)
}