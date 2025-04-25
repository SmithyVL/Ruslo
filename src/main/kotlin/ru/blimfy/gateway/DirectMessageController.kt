package ru.blimfy.gateway

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.direct.message.DirectMessageDto
import ru.blimfy.common.dto.direct.message.NewDirectMessageDto
import ru.blimfy.persistence.entity.message.toDto
import ru.blimfy.persistence.entity.message.toEntity
import ru.blimfy.security.service.TokenService
import ru.blimfy.services.conservation.ConservationService
import ru.blimfy.services.direct.DirectMessageService
import ru.blimfy.websockets.UserWebSocketService
import ru.blimfy.websockets.dto.WsMessageTypes.EDIT_DIRECT_MESSAGE
import ru.blimfy.websockets.dto.WsMessageTypes.NEW_DIRECT_MESSAGE
import ru.blimfy.websockets.dto.WsMessageTypes.REMOVE_DIRECT_MESSAGE

/**
 * Контроллер для работы с личными сообщениями.
 *
 * @property directMessageService сервис для работы с личными сообщениями.
 * @property conservationService сервис для работы с личными диалогами.
 * @property tokenService сервис для работы с токенами.
 * @property userWebSocketService сервис для работы с отправкой данных через WebSocket соединения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "DirectMessageController", description = "Контроллер для работы с личными сообщениями пользователей")
@RestController
@RequestMapping("/v1/direct-messages")
class DirectMessageController(
    private val directMessageService: DirectMessageService,
    private val conservationService: ConservationService,
    private val tokenService: TokenService,
    private val userWebSocketService: UserWebSocketService,
) {
    @Operation(summary = "Создать личное сообщение")
    @PostMapping
    suspend fun createDirectMessage(
        @RequestBody directMessageDto: DirectMessageDto,
        principal: Principal,
    ): DirectMessageDto {
        val userId = tokenService.extractUserId(principal)
        val conservationId = directMessageDto.conservationId

        // Создать сообщение в личном диалоге может только его участник.
        conservationService.checkConservationAccess(
            conservationId = conservationId,
            userId = tokenService.extractUserId(principal),
        )

        return directMessageService.saveDirectMessage(directMessageDto.toEntity(userId)).toDto()
            .apply { userWebSocketService.sendConservationMessages(conservationId, NEW_DIRECT_MESSAGE, this, userId) }
    }

    @Operation(summary = "Обновить личное сообщение")
    @PutMapping
    suspend fun modifyDirectMessage(
        @RequestBody newDirectMessageDto: NewDirectMessageDto,
        principal: Principal,
    ): DirectMessageDto {
        val userId = tokenService.extractUserId(principal)
        val conservationId = newDirectMessageDto.conservationId

        // Обновить сообщение в личном диалоге может только его участник.
        conservationService.checkConservationAccess(
            conservationId = conservationId,
            userId = tokenService.extractUserId(principal),
        )

        return directMessageService.saveDirectMessage(newDirectMessageDto.toEntity(userId)).toDto()
            .apply { userWebSocketService.sendConservationMessages(conservationId, EDIT_DIRECT_MESSAGE, this, userId) }
    }

    @Operation(summary = "Удалить личное сообщение")
    @DeleteMapping("/{directMessageId}")
    suspend fun deleteDirectMessage(@PathVariable directMessageId: UUID, principal: Principal) {
        val userId = tokenService.extractUserId(principal)
        val conservationId = directMessageService.findDirectMessage(directMessageId).conservationId

        // Удалить сообщение в личном диалоге может только его участник.
        conservationService.checkConservationAccess(
            conservationId = conservationId,
            userId = tokenService.extractUserId(principal),
        )

        directMessageService.deleteDirectMessage(id = directMessageId, authorId = userId)
            .apply {
                userWebSocketService.sendConservationMessages(
                    conservationId, REMOVE_DIRECT_MESSAGE, directMessageId, userId,
                )
            }
    }
}