package ru.blimfy.gateway.service.friend

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.user.friend.FriendDto
import ru.blimfy.gateway.dto.user.friend.FriendNickDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о запросах в друзья.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface FriendControllerService {
    /**
     * Изменяет [nickInfo] друга с идентификатором связи [friendId] для [currentUser].
     */
    suspend fun changeFriendNick(friendId: UUID, nickInfo: FriendNickDto, currentUser: User): FriendDto

    /**
     * Возвращает друзей для [currentUser].
     */
    fun findFriends(currentUser: User): Flow<FriendDto>

    /**
     * Удаляет друга на основании связи с [id].
     */
    suspend fun deleteFriend(id: UUID)
}