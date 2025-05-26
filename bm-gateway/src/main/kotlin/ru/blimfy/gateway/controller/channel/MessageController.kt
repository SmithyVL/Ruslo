package ru.blimfy.gateway.controller.channel

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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.channel.message.ModifyMessageDto
import ru.blimfy.gateway.dto.channel.message.NewMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.channel.message.MessageControllerService

/**
 * Контроллер для работы с сообщениями каналов.
 *
 * @property service сервис для обработки информации о сообщениях каналов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "MessageController", description = "Контроллер для работы с сообщениями каналов")
@RestController
@RequestMapping("/v1/channels/{channelId}/messages")
class MessageController(private val service: MessageControllerService) {
    @Operation(summary = "Получить страницу с сообщениями")
    @GetMapping
    suspend fun findMessages(
        @PathVariable channelId: UUID,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findMessages(channelId, pageNumber, pageSize, userDetails.info)

    @Operation(summary = "Создать сообщение")
    @PostMapping
    suspend fun createMessage(
        @PathVariable channelId: UUID,
        @RequestBody message: NewMessageDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.createMessage(channelId, message, userDetails.info)

    @Operation(summary = "Отредактировать сообщение")
    @PatchMapping("/{id}")
    suspend fun editMessage(
        @PathVariable channelId: UUID,
        @PathVariable id: UUID,
        @RequestBody modifyDmMessage: ModifyMessageDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.editMessage(channelId, id, modifyDmMessage, userDetails.info)

    @Operation(summary = "Удалить сообщение")
    @DeleteMapping("/{id}")
    suspend fun deleteMessage(
        @PathVariable channelId: UUID,
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteMessage(channelId, id, userDetails.info)
}