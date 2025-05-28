package ru.blimfy.gateway.api.server.ban

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.api.server.ban.handler.BanApiService
import ru.blimfy.gateway.api.server.dto.ban.NewBanDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * REST API контроллер для работы с банами серверов.
 *
 * @property service сервис для обработки информации о банах серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "BanApi", description = "REST API контроллер для работы с банами серверов")
@RestController
@RequestMapping("/v1/servers/{serverId}/bans")
class BanApi(private val service: BanApiService) {
    @Operation(summary = "Получить страницу с банами")
    @GetMapping
    suspend fun findBans(
        @PathVariable serverId: UUID,
        @RequestParam(required = false) before: UUID? = null,
        @RequestParam(required = false) after: UUID? = null,
        @RequestParam(required = false, defaultValue = "50") limit: Int,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findBans(serverId, before, after, limit, userDetails.info)

    @Operation(summary = "Получить страницу с банами по имени пользователя")
    @GetMapping("/search")
    suspend fun searchBans(
        @PathVariable serverId: UUID,
        @RequestParam query: String,
        @RequestParam(required = false, defaultValue = "50") limit: Int,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.searchBans(serverId, query, limit, userDetails.info)

    @Operation(summary = "Создать бан")
    @PutMapping("/{userId}")
    suspend fun createBan(
        @PathVariable serverId: UUID,
        @PathVariable userId: UUID,
        @RequestBody ban: NewBanDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createBan(serverId, userId, ban.reason, userDetails.info)

    @Operation(summary = "Удалить бан")
    @DeleteMapping("/{userId}")
    suspend fun removeBan(
        @PathVariable serverId: UUID,
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.removeBan(serverId, userId, userDetails.info)
}