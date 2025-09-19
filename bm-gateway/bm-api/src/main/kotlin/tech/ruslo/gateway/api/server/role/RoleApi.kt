package tech.ruslo.gateway.api.server.role

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.domain.server.api.dto.role.ModifyRoleDto
import tech.ruslo.domain.server.api.dto.role.NewRoleDto
import tech.ruslo.domain.server.api.dto.role.RolePositionDto
import tech.ruslo.gateway.api.server.role.handler.RoleApiService
import tech.ruslo.security.service.CustomUserDetails

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
        @PathVariable serverId: UUID,
        @RequestBody newRoleDto: NewRoleDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createRole(serverId, newRoleDto, userDetails.info)

    @Operation(summary = "Получить идентификаторы участников роли")
    @GetMapping("/{id}/member-ids")
    suspend fun findMemberIds(
        @PathVariable serverId: UUID,
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findMemberIds(id, serverId, userDetails.info)

    @Operation(summary = "Обновить роль")
    @PatchMapping("/{id}")
    suspend fun modifyRole(
        @PathVariable serverId: UUID,
        @PathVariable id: UUID,
        @RequestBody modifyRoleDto: ModifyRoleDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.modifyRole(id, serverId, modifyRoleDto, userDetails.info)

    @Operation(summary = "Добавить участников к роли")
    @PatchMapping("/{id}/members")
    suspend fun addRoleMembers(
        @PathVariable serverId: UUID,
        @PathVariable id: UUID,
        @RequestBody memberIds: List<UUID>,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.addRoleMembers(id, serverId, memberIds, userDetails.info)

    @Operation(summary = "Обновить позиции ролей")
    @PatchMapping
    suspend fun modifyPositions(
        @PathVariable serverId: UUID,
        @RequestBody positionDtos: List<RolePositionDto>,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.modifyPositions(serverId, positionDtos, userDetails.info)

    @Operation(summary = "Удалить роль")
    @DeleteMapping("/{id}")
    suspend fun deleteRole(
        @PathVariable serverId: UUID,
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteRole(id, serverId, userDetails.info)
}