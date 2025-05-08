package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.gateway.dto.invite.InviteDetailsDto
import ru.blimfy.gateway.dto.invite.InviteDto
import ru.blimfy.gateway.dto.invite.NewInviteDto
import ru.blimfy.gateway.dto.invite.toDto
import ru.blimfy.gateway.dto.invite.toEntity
import ru.blimfy.gateway.exception.GatewayErrors.INVALID_INVITE
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.invite.InviteService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_SERVER_MEMBER

/**
 * Контроллер для работы с приглашениями на сервера.
 *
 * @property inviteService сервис для работы с приглашениями на сервера.
 * @property serverService сервис для работы с серверами.
 * @property memberService сервис для работы с участниками серверов.
 * @property tokenService сервис для работы с токенами.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "InviteController", description = "Контроллер для работы с приглашениями на сервера")
@RestController
@RequestMapping("/v1/servers/{serverId}/invites")
class InviteController(
    private val inviteService: InviteService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val userService: UserService,
    private val tokenService: TokenService,
    private val userWebSocketStorage: UserWebSocketStorage,
) {
    @Operation(summary = "Создать приглашение на сервер")
    @PostMapping
    suspend fun createInvite(
        @PathVariable serverId: UUID,
        @RequestBody inviteDto: NewInviteDto,
        principal: Principal,
    ): InviteDto {
        val userId = tokenService.extractUserId(principal)

        // Создать приглашение на сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        return inviteService.saveInvite(inviteDto.toEntity(serverId)).toDto()
    }

    @Operation(summary = "Получить информацию о приглашении")
    @GetMapping("/{inviteId}")
    suspend fun findInvite(@PathVariable serverId: UUID, @PathVariable inviteId: UUID) =
        coroutineScope {
            val server = serverService.findServer(serverId)
            val countMembers = async { memberService.getCountServerMembers(serverId) }
            val username = async {
                val memberId = inviteService.findInvite(inviteId).authorMemberId
                val userId = memberService.findMember(memberId).userId
                userService.findUser(userId).username
            }

            InviteDetailsDto(inviteId, server.name, server.avatarUrl, countMembers.await(), username.await())
        }

    @Operation(summary = "Удалить приглашение на сервер")
    @DeleteMapping("/{inviteId}")
    suspend fun deleteInvite(
        @PathVariable serverId: UUID,
        @PathVariable inviteId: UUID,
        principal: Principal,
    ) {
        val userId = tokenService.extractUserId(principal)

        // Удалить приглашение на сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        inviteService.deleteInvite(inviteId = inviteId, serverId = serverId)
    }

    @Operation(summary = "Использовать приглашение на сервер")
    @PostMapping("/{inviteId}")
    suspend fun useInvite(
        @PathVariable serverId: UUID,
        @PathVariable inviteId: UUID,
        principal: Principal,
    ) {
        val invite = inviteService.findInvite(inviteId)

        // Проверяем соответствие приглашения серверу.
        if (invite.serverId != serverId) {
            throw IncorrectDataException(INVALID_INVITE.msg.format(inviteId, serverId))
        }

        val userId = tokenService.extractUserId(principal)
        val newMember = serverService.addNewMember(serverId = serverId, userId = userId)
        userWebSocketStorage.sendServerMessages(serverId, NEW_SERVER_MEMBER, newMember, userId)
    }
}