package ru.blimfy.gateway.api.server.member.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.gateway.api.mapper.MemberMapper
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.service.AccessService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_REMOVE
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_UPDATE

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @property accessService сервис для работы с доступами.
 * @property memberService сервис для работы с участниками серверов.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberApiServiceImpl(
    private val accessService: AccessService,
    private val memberService: MemberService,
    private val memberMapper: MemberMapper,
    private val userWsStorage: UserWebSocketStorage,
) : MemberApiService {
    override suspend fun findMembers(serverId: UUID, user: User) =
        accessService.isServerMember(serverId, user.id)
            .let { memberService.findServerMembers(serverId) }
            .map { memberMapper.toDto(it) }

    override suspend fun removeMember(serverId: UUID, userId: UUID, user: User) {
        accessService.isServerOwner(serverId, user.id)
            .let { memberService.deleteUserMember(userId, serverId) }
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_REMOVE, userId) }
    }

    override suspend fun changeMemberNick(serverId: UUID, userId: UUID, nick: String?, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { memberService.setNick(serverId, userId, nick) }
            .let { memberMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE, this) }

    override suspend fun changeCurrentMemberNick(serverId: UUID, nick: String?, user: User) =
        user.id.let { currentUserId ->
            accessService.isServerMember(serverId, currentUserId)
                .let { memberService.setNick(serverId, currentUserId, nick) }
                .let { memberMapper.toDto(it) }
                .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE, this) }
        }
}