package ru.blimfy.gateway

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.InviteDto
import ru.blimfy.integration.websockets.UserWebSocketStorage
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.security.service.TokenService
import ru.blimfy.services.invite.InviteService
import ru.blimfy.services.server.ServerService
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_SERVER_MEMBER

/**
 * Контроллер для работы с приглашениями на сервера.
 *
 * @property inviteService сервис для работы с приглашениями на сервера.
 * @property serverService сервис для работы с серверами.
 * @property tokenService сервис для работы с токенами.
 * @property userTokenWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "InviteController", description = "Контроллер для работы с приглашениями на сервера")
@RestController
@RequestMapping("/v1/invites")
class InviteController(
    private val inviteService: InviteService,
    private val serverService: ServerService,
    private val tokenService: TokenService,
    private val userTokenWebSocketStorage: UserWebSocketStorage,
) {
    @Operation(summary = "Создать/Обновить приглашение на сервер")
    @PostMapping
    suspend fun saveInvite(@RequestBody inviteDto: InviteDto, principal: Principal): InviteDto {
        val userId = tokenService.extractUserId(principal)

        // Создать приглашение на сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = inviteDto.serverId, userId = userId)

        return inviteService.saveInvite(inviteDto.toEntity()).toDto()
    }

    @Operation(summary = "Удалить приглашение на сервер")
    @DeleteMapping("/{inviteId}")
    suspend fun deleteInvite(@PathVariable inviteId: UUID, principal: Principal) {
        val userId = tokenService.extractUserId(principal)

        // Удалить приглашение на сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = inviteService.findInvite(inviteId).serverId, userId = userId)

        inviteService.deleteInvite(inviteId)
    }

    @Operation(summary = "Использовать приглашение на сервер")
    @PostMapping("/{inviteId}")
    suspend fun useInvite(@PathVariable inviteId: UUID, principal: Principal) =
        inviteService.findInvite(inviteId)
            .apply {
                val userId = tokenService.extractUserId(principal)
                val newMember = serverService.addNewMember(serverId = serverId, userId = userId)
                userTokenWebSocketStorage.sendServerMessages(serverId, NEW_SERVER_MEMBER, newMember, userId)
            }
            .serverId
}