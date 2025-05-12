package ru.blimfy.gateway.controller

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
import ru.blimfy.gateway.dto.message.text.NewTextMessageDto
import ru.blimfy.gateway.dto.message.text.TextMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.textmessage.TextMessageControllerService

/**
 * Контроллер для работы с сообщениями каналов серверов.
 *
 * @property textMessageControllerService сервис для обработки информации о текстовых сообщениях каналов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "TextMessageController", description = "Контроллер для работы с сообщениями каналов серверов")
@RestController
@RequestMapping("/v1/text-messages")
class TextMessageController(private val textMessageControllerService: TextMessageControllerService) {
    @Operation(summary = "Создать текстовое сообщение")
    @PostMapping
    suspend fun createTextMessage(
        @RequestBody newMessage: NewTextMessageDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = textMessageControllerService.createMessage(newMessage, user)

    @Operation(summary = "Обновить текстовое сообщение")
    @PutMapping
    suspend fun modifyTextMessage(
        @RequestBody message: TextMessageDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = textMessageControllerService.modifyMessage(message, user)

    @Operation(summary = "Удалить текстовое сообщение")
    @DeleteMapping("/{messageId}")
    suspend fun deleteTextMessage(
        @PathVariable messageId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = textMessageControllerService.deleteMessage(messageId, user)
}