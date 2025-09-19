package tech.ruslo.gateway.api.channel.message

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
import tech.ruslo.domain.channel.api.dto.message.ModifyMessageDto
import tech.ruslo.domain.channel.api.dto.message.NewMessageDto
import tech.ruslo.gateway.api.channel.message.handler.MessageApiService
import tech.ruslo.security.service.CustomUserDetails

/**
 * REST API контроллер для работы с сообщениями каналов.
 *
 * @property service сервис для обработки информации о сообщениях каналов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "MessageApi", description = "REST API контроллер для работы с сообщениями каналов")
@RestController
@RequestMapping("/v1/channels/{channelId}/messages")
class MessageApi(private val service: MessageApiService) {
    @Operation(summary = "Получить страницу с сообщениями")
    @GetMapping
    suspend fun findMessages(
        @PathVariable channelId: UUID,
        @RequestParam(required = false) around: UUID? = null,
        @RequestParam(required = false) before: UUID? = null,
        @RequestParam(required = false) after: UUID? = null,
        @RequestParam(required = false, defaultValue = "50") limit: Int,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findMessages(channelId, around, before, after, limit, userDetails.info)

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
        @RequestBody message: ModifyMessageDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.editMessage(channelId, id, message, userDetails.info)

    @Operation(summary = "Удалить сообщение")
    @DeleteMapping("/{id}")
    suspend fun deleteMessage(
        @PathVariable channelId: UUID,
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.deleteMessage(channelId, id, userDetails.info)
}