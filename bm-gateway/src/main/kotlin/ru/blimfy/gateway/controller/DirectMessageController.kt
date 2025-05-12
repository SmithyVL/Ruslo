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
import ru.blimfy.gateway.dto.message.direct.DirectMessageDto
import ru.blimfy.gateway.dto.message.direct.NewDirectMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.directmessage.DirectMessageControllerService

/**
 * Контроллер для работы с личными сообщениями.
 *
 * @property directMessageControllerService сервис для обработки информации о личных сообщениях.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "DirectMessageController", description = "Контроллер для работы с личными сообщениями пользователей")
@RestController
@RequestMapping("/v1/direct-messages")
class DirectMessageController(private val directMessageControllerService: DirectMessageControllerService) {
    @Operation(summary = "Создать личное сообщение")
    @PostMapping
    suspend fun createDirectMessage(
        @RequestBody directMessageDto: DirectMessageDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = directMessageControllerService.createDirectMessage(directMessageDto, user)

    @Operation(summary = "Обновить личное сообщение")
    @PutMapping
    suspend fun modifyDirectMessage(
        @RequestBody newDirectMessageDto: NewDirectMessageDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = directMessageControllerService.modifyDirectMessage(newDirectMessageDto, user)

    @Operation(summary = "Удалить личное сообщение")
    @DeleteMapping("/{directMessageId}")
    suspend fun deleteDirectMessage(
        @PathVariable directMessageId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = directMessageControllerService.deleteDirectMessage(directMessageId, user)
}