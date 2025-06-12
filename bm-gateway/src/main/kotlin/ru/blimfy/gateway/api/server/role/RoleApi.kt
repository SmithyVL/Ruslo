package ru.blimfy.gateway.api.server.member

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import ru.blimfy.gateway.api.server.dto.role.ModifyRoleDto
import ru.blimfy.gateway.api.server.dto.role.NewRoleDto
import ru.blimfy.gateway.api.server.role.handler.RoleApiService
import ru.blimfy.gateway.integration.security.CustomUserDetails
import java.util.*

/**
 * REST API контроллер для работы с ролями серверов.
 *
 * @property service сервис для обработки информации о ролях серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "RoleApi", description = "REST API контроллер для работы с ролями серверов")
@RestController
@RequestMapping("/v1/servers/{serverId}/roles")
class RoleApi(private val service: RoleApiService) {
    @Operation(summary = "Создать роль")
    @PostMapping
    suspend fun createRole(
        @RequestBody newRoleDto: NewRoleDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createServer(newRoleDto, userDetails.info)

    @Operation(summary = "Обновить роль")
    @PutMapping("/{id}")
    suspend fun modifyRole(
        @PathVariable id: UUID,
        @RequestBody modifyRoleDto: ModifyRoleDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.modifyServer(id, modifyRoleDto, userDetails.info)
}