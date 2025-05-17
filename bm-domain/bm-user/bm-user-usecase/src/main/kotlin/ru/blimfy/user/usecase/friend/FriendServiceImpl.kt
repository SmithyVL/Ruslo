package ru.blimfy.user.usecase.friend

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.user.db.entity.Friend
import ru.blimfy.user.db.repository.FriendRepository
import ru.blimfy.user.usecase.exception.UserErrors.FRIEND_NOT_FOUND

/**
 * Реализация интерфейса для работы с друзьями.
 *
 * @property friendRepo репозиторий для работы с друзьями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class FriendServiceImpl(private val friendRepo: FriendRepository) : FriendService {
    override suspend fun createFriend(friend: Friend) =
        friendRepo.save(friend)

    override suspend fun changeFriendNick(id: UUID, newNick: String, userId: UUID) =
        findFriend(id).apply { nick = newNick }.let { friendRepo.save(it) }

    override suspend fun findFriend(fromId: UUID, toId: UUID) =
        friendRepo.findByFromIdAndToId(fromId = fromId, toId = toId)

    override fun findFriends(fromId: UUID) =
        friendRepo.findAllByFromId(fromId)

    override suspend fun deleteFriend(id: UUID) {
        findFriend(id).apply {
            friendRepo.deleteByIdAndFromId(id = id, fromId = fromId)
            friendRepo.deleteByIdAndFromId(id = id, fromId = toId)
        }
    }

    /**
     * Возвращает друга с [id].
     */
    private suspend fun findFriend(id: UUID) =
        friendRepo.findById(id) ?: throw NotFoundException(FRIEND_NOT_FOUND.msg)
}