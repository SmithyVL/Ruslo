package ru.blimfy.gateway.controller.channel

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.channel.group.GroupControllerService

/**
 * Контроллер для работы с группой.
 *
 * @property service сервис для обработки информации о группах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "GroupController", description = "Контроллер для работы с группой")
@RestController
@RequestMapping("/v1/channels/{id}")
class GroupController(private val service: GroupControllerService) {
    @Operation(summary = "Передать группу другому владельцу")
    @PutMapping("/owner/{userId}")
    suspend fun changeOwner(
        @PathVariable id: UUID,
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changeOwner(id, userId, userDetails.info)

    @Operation(summary = "Добавить участников в группу")
    @PutMapping("/recipients")
    suspend fun addRecipients(
        @PathVariable id: UUID,
        @RequestBody recipients: Set<UUID>,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.addRecipients(id, recipients, userDetails.info)

    @Operation(summary = "Удалить участника из группы")
    @DeleteMapping("/recipients/{userId}")
    suspend fun removeRecipient(
        @PathVariable id: UUID,
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.removeRecipient(id, userId, userDetails.info)

    @Operation(summary = "Создать приглашение для группы")
    @PostMapping("/invites")
    suspend fun createGroupInvite(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createGroupInvite(id, userDetails.info)
}