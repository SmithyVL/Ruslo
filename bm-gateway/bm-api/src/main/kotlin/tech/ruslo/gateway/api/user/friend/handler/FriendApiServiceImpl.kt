package tech.ruslo.gateway.api.user.friend.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.domain.user.usecase.friend.FriendService
import tech.ruslo.gateway.mapper.FriendMapper

/**
 * Реализация интерфейса для работы с обработкой друзей.
 *
 * @property friendService сервис для работы с друзьями.
 * @property friendMapper маппер для работы с друзьями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class FriendApiServiceImpl(
    private val friendService: FriendService,
    private val friendMapper: FriendMapper,
) : FriendApiService {
    override fun findUserFriends(user: User) =
        friendService.findFriends(user.id)
            .map { friendMapper.toDto(it) }

    override suspend fun changeFriendNick(userId: UUID, nick: String?, user: User) =
        friendService.changeFriendNick(userId, user.id, nick)
            .let { friendMapper.toDto(it) }

    override suspend fun deleteFriend(userId: UUID, user: User) =
        friendService.deleteFriend(user.id, userId)
}