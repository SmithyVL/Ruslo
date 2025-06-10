package ru.blimfy.gateway.api.server.member.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.gateway.api.mapper.MemberMapper
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_REMOVE
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_UPDATE

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberApiServiceImpl(
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val memberMapper: MemberMapper,
    private val userWsStorage: UserWebSocketStorage,
) : MemberApiService {
    override suspend fun findMembers(serverId: UUID, user: User) =
        // Получить участников сервера может только его участник.
        serverService.checkServerView(serverId, user.id)
            .let { memberService.findServerMembers(serverId) }
            .map { memberMapper.toDto(it) }

    override suspend fun removeMember(serverId: UUID, userId: UUID, user: User) {
        // Удалить участника сервера может только его создатель.
        serverService.checkServerWrite(serverId, user.id)
            .let { memberService.deleteUserMember(userId, serverId) }
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_REMOVE, userId) }
    }

    override suspend fun changeMemberNick(serverId: UUID, userId: UUID, nick: String?, user: User) =
        // Изменить ник участника на сервере может только его создатель.
        serverService.checkServerWrite(serverId, user.id)
            .let { memberService.setNick(serverId, userId, nick) }
            .let { memberMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE, this) }

    override suspend fun changeCurrentMemberNick(serverId: UUID, nick: String?, user: User) =
        user.id.let { currentUserId ->
            // Изменить свой ник на сервере может только его участник.
            serverService.checkServerView(serverId, currentUserId)
                .let { memberService.setNick(serverId, currentUserId, nick) }
                .let { memberMapper.toDto(it) }
                .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE, this) }
        }
}