package ru.blimfy.gateway.api.channel.invite

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.api.channel.invite.handler.InviteApiService
import ru.blimfy.security.service.CustomUserDetails

/**
 * REST API контроллер для работы с приглашениями.
 *
 * @property service сервис для обработки информации о приглашениях.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "InviteApi", description = "REST API контроллер для работы с приглашениями на сервера")
@RestController
@RequestMapping("/v1/invites")
class InviteApi(private val service: InviteApiService) {
    @Operation(summary = "Получить информацию о приглашении")
    @GetMapping("/{id}")
    suspend fun findInvite(@PathVariable id: UUID) =
        service.findInvite(id)

    @Operation(summary = "Удалить приглашение")
    @DeleteMapping("/{id}")
    suspend fun deleteInvite(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteInvite(id, userDetails.info)

    @Operation(summary = "Использовать приглашение")
    @PostMapping("/{id}")
    suspend fun useInvite(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.useInvite(id, userDetails.info)
}