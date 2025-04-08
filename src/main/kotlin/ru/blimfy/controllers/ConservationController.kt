package ru.blimfy.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.CreateConservationDto
import ru.blimfy.services.conservation.ConservationService
import ru.blimfy.services.direct.DirectMessageService

/**
 * REST API контроллер для работы с личными диалогами.
 *
 * @property conservationService сервис для работы с личными диалогами.
 * @property directMessageService сервис для работы с личными сообщениями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ConservationController", description = "REST API для работы с личными диалогами пользователей")
@RestController
@RequestMapping("/v1/conservations")
class ConservationController(
    private val conservationService: ConservationService,
    private val directMessageService: DirectMessageService,
) {
    @Operation(summary = "Создать личный диалог")
    @PostMapping
    suspend fun createConservation(@RequestBody createConservationDto: CreateConservationDto) =
        conservationService.createConservation(createConservationDto.firstUserId, createConservationDto.secondUserId)

    @Operation(summary = "Получить сообщения личного диалога")
    @GetMapping("/{conservationId}/messages")
    suspend fun findConservationDirectMessages(@PathVariable conservationId: UUID) =
        directMessageService.findConservationDirectMessages(conservationId)
}