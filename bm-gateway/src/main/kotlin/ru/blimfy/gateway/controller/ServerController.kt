package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.server.NewServerDto
import ru.blimfy.gateway.dto.server.ServerDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.server.ServerControllerService

/**
 * Контроллер для работы с серверами.
 *
 * @property serverControllerService сервис для обработки информации о серверах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ServerController", description = "Контроллер для работы с серверами пользователей")
@RestController
@RequestMapping("/v1/servers")
class ServerController(private val serverControllerService: ServerControllerService) {
    @Operation(summary = "Создать сервер")
    @PostMapping
    suspend fun createServer(
        @RequestBody newServerDto: NewServerDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.createServer(newServerDto, user)

    @Operation(summary = "Обновить сервер")
    @PutMapping
    suspend fun modifyServer(
        @RequestBody serverDto: ServerDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.modifyServer(serverDto, user)

    @Operation(summary = "Получить сервер")
    @GetMapping("/{serverId}")
    suspend fun findServer(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.findServer(serverId, user)

    @Operation(summary = "Удалить сервер")
    @DeleteMapping("/{serverId}")
    suspend fun deleteServer(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.deleteServer(serverId, user)

    @Operation(summary = "Кикнуть участника сервера")
    @DeleteMapping("/{serverId}/member/{memberId}")
    suspend fun deleteServerMember(
        @PathVariable serverId: UUID,
        @PathVariable memberId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.deleteServerMember(serverId, memberId, user)

    @Operation(summary = "Получить всех участников сервера")
    @GetMapping("/{serverId}/members")
    suspend fun findServerMembers(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.findServerMembers(serverId, user)

    @Operation(summary = "Получить все каналы сервера")
    @GetMapping("/{serverId}/channels")
    suspend fun findServerChannels(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.findServerChannels(serverId, user)

    @Operation(summary = "Получить все приглашения сервера")
    @GetMapping("/{serverId}/invites")
    suspend fun findServerInvites(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = serverControllerService.findServerInvites(serverId, user)
}