package ru.blimfy.gateway.service.invite

import java.util.UUID
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.server.invite.InviteDetailsDto
import ru.blimfy.gateway.dto.server.invite.InviteDto
import ru.blimfy.gateway.dto.server.invite.NewInviteDto
import ru.blimfy.gateway.dto.server.invite.toDto
import ru.blimfy.gateway.dto.server.invite.toEntity
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.invite.InviteService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_SERVER_MEMBER

/**
 * Реализация интерфейса для работы с обработкой запросов о приглашениях серверов.
 *
 * @property inviteService сервис для работы с приглашениями на сервера.
 * @property serverService сервис для работы с серверами.
 * @property memberService сервис для работы с участниками серверов.
 * @property userService сервис для работы с пользователями.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class InviteControllerServiceImpl(
    private val inviteService: InviteService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val userService: UserService,
    private val userWebSocketStorage: UserWebSocketStorage,
) : InviteControllerService {
    override suspend fun createInvite(newInviteDto: NewInviteDto, currentUser: User): InviteDto {
        // Создать приглашение на сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = newInviteDto.serverId, userId = currentUser.id)

        return inviteService.saveInvite(newInviteDto.toEntity()).toDto()
    }

    override suspend fun findInvite(inviteId: UUID) = coroutineScope {
        val invite = inviteService.findInvite(inviteId)
        val serverId = invite.serverId

        val server = serverService.findServer(serverId)
        val countMembers = async { memberService.getCountServerMembers(serverId) }
        val username = async { userService.findUser(memberService.findMember(invite.authorMemberId).userId).username }

        InviteDetailsDto(inviteId, server.name, server.icon, countMembers.await(), username.await())
    }

    override suspend fun deleteInvite(inviteId: UUID, currentUser: User) {
        val userId = currentUser.id
        val serverId = inviteService.findInvite(inviteId).serverId

        // Удалить приглашение на сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        inviteService.deleteInvite(inviteId = inviteId, serverId = serverId)
    }

    override suspend fun useInvite(inviteId: UUID, currentUser: User) {
        val userId = currentUser.id
        val serverId = inviteService.findInvite(inviteId).serverId
        val newMember = serverService.addNewMember(serverId = serverId, userId = userId)
        userWebSocketStorage.sendServerMessages(serverId, NEW_SERVER_MEMBER, newMember, userId)
    }
}