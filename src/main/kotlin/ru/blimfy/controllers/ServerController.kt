package ru.blimfy.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.ServerDto
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.security.TokenService
import ru.blimfy.services.channel.ChannelService
import ru.blimfy.services.invite.InviteService
import ru.blimfy.services.member.MemberService
import ru.blimfy.services.server.ServerService

/**
 * REST API контроллер для работы с серверами.
 *
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @property inviteService сервис для работы с приглашениями серверов.
 * @property tokenService сервис для работы с токенами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ServerController", description = "REST API для работы с серверами пользователей")
@RestController
@RequestMapping("/v1/servers")
class ServerController(
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val memberService: MemberService,
    private val inviteService: InviteService,
    private val tokenService: TokenService,
) {
    @Operation(summary = "Создать/Обновить сервер")
    @PostMapping
    suspend fun saveServer(@RequestBody serverDto: ServerDto) =
        serverService.saveServer(serverDto.toEntity()).toDto()

    @Operation(summary = "Получить сервер")
    @GetMapping("/{serverId}")
    suspend fun findServer(@PathVariable serverId: UUID) =
        serverService.findServer(serverId).toDto()

    @Operation(summary = "Удалить сервер")
    @DeleteMapping("/{serverId}")
    suspend fun deleteServer(@PathVariable serverId: UUID, principal: Principal) =
        serverService.deleteServer(tokenService.extractUserId(principal), serverId)

    @Operation(summary = "Кикнуть участника сервера")
    @DeleteMapping("/{serverId}/member/{memberId}")
    suspend fun deleteServerMember(@PathVariable serverId: UUID, @PathVariable memberId: UUID) =
        memberService.deleteServerMember(memberId, serverId)

    @Operation(summary = "Получить всех участников сервера")
    @GetMapping("/{serverId}/members")
    suspend fun findServerMembers(@PathVariable serverId: UUID) =
        memberService.findServerMembers(serverId).map { it.toDto() }

    @Operation(summary = "Получить все каналы сервера")
    @GetMapping("/{serverId}/channels")
    suspend fun findServerChannels(@PathVariable serverId: UUID) =
        channelService.findServerChannels(serverId).map { it.toDto() }

    @Operation(summary = "Получить все приглашения сервера")
    @GetMapping("/{serverId}/invites")
    suspend fun findServerInvites(@PathVariable serverId: UUID) =
        inviteService.findServerInvites(serverId).map { it.toDto() }
}