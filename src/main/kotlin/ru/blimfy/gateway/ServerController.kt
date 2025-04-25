package ru.blimfy.gateway

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.InviteDto
import ru.blimfy.common.dto.MemberDto
import ru.blimfy.common.dto.channel.ChannelDto
import ru.blimfy.common.dto.server.NewServerDto
import ru.blimfy.common.dto.server.ServerDto
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.security.service.TokenService
import ru.blimfy.services.channel.ChannelService
import ru.blimfy.services.invite.InviteService
import ru.blimfy.services.member.MemberService
import ru.blimfy.services.server.ServerService
import ru.blimfy.websockets.UserWebSocketService
import ru.blimfy.websockets.dto.WsMessageTypes.EDIT_SERVER
import ru.blimfy.websockets.dto.WsMessageTypes.REMOVE_SERVER_MEMBER

/**
 * Контроллер для работы с серверами.
 *
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @property inviteService сервис для работы с приглашениями серверов.
 * @property tokenService сервис для работы с токенами.
 * @property userWebSocketService сервис для работы с отправкой данных через WebSocket соединения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ServerController", description = "Контроллер для работы с серверами пользователей")
@RestController
@RequestMapping("/v1/servers")
class ServerController(
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val memberService: MemberService,
    private val inviteService: InviteService,
    private val tokenService: TokenService,
    private val userWebSocketService: UserWebSocketService,
) {
    @Operation(summary = "Создать сервер")
    @PostMapping
    suspend fun createServer(@RequestBody newServerDto: NewServerDto, principal: Principal) =
        serverService.createServer(newServerDto.toEntity(tokenService.extractUserId(principal))).toDto()

    @Operation(summary = "Обновить сервер")
    @PutMapping
    suspend fun modifyServer(@RequestBody serverDto: ServerDto, principal: Principal): ServerDto {
        val userId = tokenService.extractUserId(principal)
        val serverId = serverDto.id

        // Обновить сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        return serverService.modifyServer(serverDto.toEntity(userId)).toDto()
            .apply { userWebSocketService.sendServerMessages(serverId, EDIT_SERVER, this, userId) }
    }

    @Operation(summary = "Получить сервер")
    @GetMapping("/{serverId}")
    suspend fun findServer(@PathVariable serverId: UUID, principal: Principal): ServerDto {
        // Получить сервер может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = tokenService.extractUserId(principal))

        return serverService.findServer(serverId).toDto()
    }

    @Operation(summary = "Удалить сервер")
    @DeleteMapping("/{serverId}")
    suspend fun deleteServer(@PathVariable serverId: UUID, principal: Principal) =
        serverService.deleteServer(id = serverId, ownerId = tokenService.extractUserId(principal))

    @Operation(summary = "Кикнуть участника сервера")
    @DeleteMapping("/{serverId}/member/{memberId}")
    suspend fun deleteServerMember(
        @PathVariable serverId: UUID,
        @PathVariable memberId: UUID,
        principal: Principal,
    ) {
        val userId = tokenService.extractUserId(principal)

        // Кикнуть участника сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        memberService.deleteServerMember(memberId = memberId, serverId = serverId)
            .apply { userWebSocketService.sendServerMessages(serverId, REMOVE_SERVER_MEMBER, memberId, userId) }
    }

    @Operation(summary = "Получить всех участников сервера")
    @GetMapping("/{serverId}/members")
    suspend fun findServerMembers(@PathVariable serverId: UUID, principal: Principal): Flow<MemberDto> {
        // Получить участников сервера может только его участник.
        serverService.checkServerViewAccess(
            serverId = serverId,
            userId = tokenService.extractUserId(principal),
        )

        return memberService.findServerMembers(serverId).map { it.toDto() }
    }

    @Operation(summary = "Получить все каналы сервера")
    @GetMapping("/{serverId}/channels")
    suspend fun findServerChannels(@PathVariable serverId: UUID, principal: Principal): Flow<ChannelDto> {
        // Получить каналы сервера может только его участник.
        serverService.checkServerViewAccess(
            serverId = serverId,
            userId = tokenService.extractUserId(principal),
        )

        return channelService.findServerChannels(serverId).map { it.toDto() }
    }

    @Operation(summary = "Получить все приглашения сервера")
    @GetMapping("/{serverId}/invites")
    suspend fun findServerInvites(@PathVariable serverId: UUID, principal: Principal): Flow<InviteDto> {
        // Получить приглашения сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = tokenService.extractUserId(principal))

        return inviteService.findServerInvites(serverId).map { it.toDto() }
    }
}