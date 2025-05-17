package ru.blimfy.gateway.controller.server

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.server.invite.NewInviteDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.invite.InviteControllerService

/**
 * Контроллер для работы с приглашениями на сервера.
 *
 * @property inviteControllerService сервис для обработки информации о приглашениях серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "InviteController", description = "Контроллер для работы с приглашениями на сервера")
@RestController
@RequestMapping("/v1/invites")
class InviteController(private val inviteControllerService: InviteControllerService) {
    @Operation(summary = "Создать приглашение на сервер")
    @PostMapping
    suspend fun createInvite(
        @RequestBody inviteDto: NewInviteDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = inviteControllerService.createInvite(inviteDto, userDetails.info)

    @Operation(summary = "Получить информацию о приглашении")
    @GetMapping("/{inviteId}")
    suspend fun findInvite(@PathVariable inviteId: UUID) =
        inviteControllerService.findInvite(inviteId)

    @Operation(summary = "Удалить приглашение на сервер")
    @DeleteMapping("/{inviteId}")
    suspend fun deleteInvite(
        @PathVariable inviteId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = inviteControllerService.deleteInvite(inviteId, userDetails.info)

    @Operation(summary = "Использовать приглашение на сервер")
    @PostMapping("/{inviteId}")
    suspend fun useInvite(
        @PathVariable inviteId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = inviteControllerService.useInvite(inviteId, userDetails.info)
}