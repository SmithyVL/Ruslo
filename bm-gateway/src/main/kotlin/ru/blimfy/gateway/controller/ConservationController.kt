package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.conservation.NewConservationDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.conservation.ConservationControllerService

/**
 * Контроллер для работы с личными диалогами.
 *
 * @property conservationControllerService сервис для обработки информации о личных диалогах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ConservationController", description = "Контроллер для работы с личными диалогами пользователей")
@RestController
@RequestMapping("/v1/conservations")
class ConservationController(private val conservationControllerService: ConservationControllerService) {
    @Operation(summary = "Создать личный диалог")
    @PostMapping
    suspend fun createConservation(@RequestBody newConservationDto: NewConservationDto) =
        conservationControllerService.createConservation(newConservationDto)

    @Operation(summary = "Получить страницу с сообщениями личного диалога")
    @GetMapping("/{conservationId}/messages")
    suspend fun findConservationDirectMessages(
        @PathVariable conservationId: UUID,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) =
        conservationControllerService.findConservationDirectMessages(conservationId, pageNumber, pageSize, user)
}