package ru.blimfy.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.InviteDto
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.services.invite.InviteService

/**
 * REST API контроллер для работы с приглашениями на сервера.
 *
 * @property inviteService сервис для работы с приглашениями на сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "InviteController", description = "REST API для работы с приглашениями на сервера")
@RestController
@RequestMapping("/v1/invites")
class InviteController(private val inviteService: InviteService) {
    @Operation(summary = "Создать/Обновить приглашение на сервер")
    @PostMapping
    suspend fun saveInvite(@RequestBody inviteDto: InviteDto) =
        inviteService.saveInvite(inviteDto.toEntity()).toDto()

    @Operation(summary = "Получить приглашение на сервер")
    @GetMapping("/{inviteId}")
    suspend fun findInvite(@PathVariable inviteId: UUID) = inviteService.findInvite(inviteId).toDto()

    @Operation(summary = "Удалить приглашение на сервер")
    @DeleteMapping("/{inviteId}")
    suspend fun deleteInvite(@PathVariable inviteId: UUID) = inviteService.deleteInvite(inviteId)
}