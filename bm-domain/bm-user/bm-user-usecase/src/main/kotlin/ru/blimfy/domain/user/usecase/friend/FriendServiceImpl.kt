package ru.blimfy.domain.user.usecase.friend

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.domain.user.db.entity.Friend
import ru.blimfy.domain.user.db.repository.FriendRepository
import ru.blimfy.domain.user.usecase.exception.UserErrors.FRIEND_NOT_FOUND

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

    override suspend fun changeFriendNick(toId: UUID, fromId: UUID, nick: String?): Friend {
        val friend = friendRepo.findByFromIdAndToId(fromId, toId) ?: throw NotFoundException(FRIEND_NOT_FOUND.msg)

        return friend.apply { this.nick = nick }.let { friendRepo.save(it) }
    }

    override fun findFriends(fromId: UUID) =
        friendRepo.findAllByFromId(fromId)

    @Transactional
    override suspend fun deleteFriend(fromId: UUID, toId: UUID) {
        friendRepo.deleteByFromIdAndToId(fromId = fromId, toId = toId)
        friendRepo.deleteByFromIdAndToId(fromId = toId, toId = fromId)
    }
}