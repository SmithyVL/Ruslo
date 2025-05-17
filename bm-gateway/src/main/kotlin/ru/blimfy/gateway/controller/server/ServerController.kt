package ru.blimfy.gateway.controller.server

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.server.ModifyServerDto
import ru.blimfy.gateway.dto.server.NewServerDto
import ru.blimfy.gateway.dto.server.ServerOwnerDto
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
        @RequestBody newServer: NewServerDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.createServer(newServer, userDetails.info)

    @Operation(summary = "Обновить сервер")
    @PutMapping("/{serverId}")
    suspend fun modifyServer(
        @PathVariable serverId: UUID,
        @RequestBody modifyServer: ModifyServerDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.modifyServer(serverId, modifyServer, userDetails.info)

    @Operation(summary = "Передать владение сервером")
    @PatchMapping("/{serverId}")
    suspend fun changeOwner(
        @PathVariable serverId: UUID,
        @RequestBody serverOwner: ServerOwnerDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.changeOwner(serverId, serverOwner, userDetails.info)

    @Operation(summary = "Получить сервер")
    @GetMapping("/{serverId}")
    suspend fun findServer(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.findServer(serverId, userDetails.info)

    @Operation(summary = "Удалить сервер")
    @DeleteMapping("/{serverId}")
    suspend fun deleteServer(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.deleteServer(serverId, userDetails.info)

    @Operation(summary = "Кикнуть участника сервера")
    @DeleteMapping("/{serverId}/member/{memberId}")
    suspend fun deleteServerMember(
        @PathVariable serverId: UUID,
        @PathVariable memberId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.deleteServerMember(serverId, memberId, userDetails.info)

    @Operation(summary = "Получить всех участников сервера")
    @GetMapping("/{serverId}/members")
    suspend fun findServerMembers(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.findServerMembers(serverId, userDetails.info)

    @Operation(summary = "Получить все каналы сервера")
    @GetMapping("/{serverId}/channels")
    suspend fun findServerChannels(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.findServerChannels(serverId, userDetails.info)

    @Operation(summary = "Получить все приглашения сервера")
    @GetMapping("/{serverId}/invites")
    suspend fun findServerInvites(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = serverControllerService.findServerInvites(serverId, userDetails.info)
}