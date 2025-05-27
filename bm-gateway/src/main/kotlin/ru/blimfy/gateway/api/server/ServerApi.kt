package ru.blimfy.gateway.api.server

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
import ru.blimfy.gateway.dto.server.channel.ChannelPositionDto
import ru.blimfy.gateway.dto.server.channel.ServerChannelDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.server.ServerApiService

/**
 * REST API контроллер для работы с серверами.
 *
 * @property service сервис для обработки информации о серверах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ServerApi", description = "REST API контроллер для работы с серверами пользователей")
@RestController
@RequestMapping("/v1/servers")
class ServerApi(private val service: ServerApiService) {
    @Operation(summary = "Создать сервер")
    @PostMapping
    suspend fun createServer(
        @RequestBody newServer: NewServerDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createServer(newServer, userDetails.info)

    @Operation(summary = "Получить сервер")
    @GetMapping("/{id}")
    suspend fun findServer(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findServer(id, userDetails.info)

    @Operation(summary = "Обновить сервер")
    @PutMapping("/{id}")
    suspend fun modifyServer(
        @PathVariable id: UUID,
        @RequestBody modifyServer: ModifyServerDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.modifyServer(id, modifyServer, userDetails.info)

    @Operation(summary = "Изменить владельца сервером")
    @PutMapping("/{id}/owner/{userId}")
    suspend fun changeOwner(
        @PathVariable id: UUID,
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changeOwner(id, userId, userDetails.info)

    @Operation(summary = "Удалить сервер")
    @DeleteMapping("/{id}")
    suspend fun deleteServer(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteServer(id, userDetails.info)

    @Operation(summary = "Получить каналы сервера")
    @GetMapping("/{id}/channels")
    suspend fun findChannels(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findServerChannels(id, userDetails.info)

    @Operation(summary = "Создать канал сервера")
    @PostMapping("/{id}/channels")
    suspend fun createChannel(
        @PathVariable id: UUID,
        @RequestBody channel: ServerChannelDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createChannel(id, channel, userDetails.info)

    @Operation(summary = "Изменить позиции каналов сервера")
    @PatchMapping("/{id}/channels")
    suspend fun modifyServerChannelPositions(
        @PathVariable id: UUID,
        @RequestBody positions: List<ChannelPositionDto>,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.modifyServerChannelPositions(id, positions, userDetails.info)

    @Operation(summary = "Получить приглашения сервера")
    @GetMapping("/{id}/invites")
    suspend fun findInvites(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findServerInvites(id, userDetails.info)

    @Operation(summary = "Создать приглашение на канал сервера")
    @PostMapping("/{id}/channels/{channelId}/invites")
    suspend fun createInvite(
        @PathVariable id: UUID,
        @PathVariable channelId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createInvite(id, channelId, userDetails.info)
}