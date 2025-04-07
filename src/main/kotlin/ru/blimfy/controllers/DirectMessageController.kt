package ru.blimfy.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.DirectMessageDto
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.services.direct.DirectMessageService

/**
 * REST API контроллер для работы с личными сообщениями.
 *
 * @property directMessageService сервис для работы с личными сообщениями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "DirectMessageController", description = "REST API для работы с личными сообщениями пользователей")
@RestController
@RequestMapping("/v1/direct-messages")
class DirectMessageController(private val directMessageService: DirectMessageService) {
    @Operation(summary = "Создать/Обновить личное сообщение")
    @PostMapping
    suspend fun saveDirectMessage(@RequestBody directMessageDto: DirectMessageDto) =
        directMessageService.saveDirectMessage(directMessageDto.toEntity()).toDto()

    @Operation(summary = "Удалить личное сообщение")
    @DeleteMapping("/{directMessageId}")
    suspend fun deleteDirectMessage(@PathVariable directMessageId: UUID) =
        directMessageService.deleteDirectMessage(directMessageId)
}