package ru.blimfy.gateway.controller.server

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.server.message.NewTextMessageDto
import ru.blimfy.gateway.dto.server.message.TextMessageContentDto
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
    suspend fun createMessage(
        @RequestBody newMessage: NewTextMessageDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = textMessageControllerService.createMessage(newMessage, userDetails.info)

    @Operation(summary = "Изменить содержимое сообщения")
    @PatchMapping("/{messageId}")
    suspend fun changeContent(
        @PathVariable messageId: UUID,
        @RequestBody messageContent: TextMessageContentDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = textMessageControllerService.changeContent(messageId, messageContent, userDetails.info)

    @Operation(summary = "Удалить текстовое сообщение")
    @DeleteMapping("/{messageId}")
    suspend fun deleteMessage(
        @PathVariable messageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = textMessageControllerService.deleteMessage(messageId, userDetails.info)

    @Operation(summary = "Закрепить текстовое сообщение")
    @PutMapping("/{messageId}/pins")
    suspend fun pinMessage(
        @PathVariable messageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = textMessageControllerService.setMessagePinned(messageId, true, userDetails.info)

    @Operation(summary = "Открепить текстовое сообщение")
    @DeleteMapping("/{messageId}/pins")
    suspend fun unpinMessage(
        @PathVariable messageId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = textMessageControllerService.setMessagePinned(messageId, false, userDetails.info)
}