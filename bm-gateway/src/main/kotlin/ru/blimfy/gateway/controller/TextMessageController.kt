package ru.blimfy.gateway.controller

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
import ru.blimfy.gateway.dto.message.text.TextMessageDto
import ru.blimfy.gateway.dto.message.text.toDto
import ru.blimfy.gateway.dto.message.text.toEntity
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.channel.ChannelService
import ru.blimfy.server.usecase.message.TextMessageService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_TEXT_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_TEXT_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_TEXT_MESSAGE

/**
 * Контроллер для работы с сообщениями каналов серверов.
 *
 * @property textMessageService сервис для работы с текстовыми сообщениями каналов серверов.
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами.
 * @property tokenService сервис для работы с токенами.
 * @property userTokenWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "TextMessageController", description = "Контроллер для работы с сообщениями каналов серверов")
@RestController
@RequestMapping("/v1/text-messages")
class TextMessageController(
    private val textMessageService: TextMessageService,
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val tokenService: TokenService,
    private val userTokenWebSocketStorage: UserWebSocketStorage,
) {
    @Operation(summary = "Создать текстовое сообщение")
    @PostMapping
    suspend fun createTextMessage(
        @RequestBody textMessageDto: TextMessageDto,
        principal: Principal,
    ): TextMessageDto {
        val userId = tokenService.extractUserId(principal)
        val serverId = channelService.findChannel(textMessageDto.channelId).serverId

        // Отправить текстовое сообщение в канал сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        return textMessageService.saveMessage(textMessageDto.toEntity(userId)).toDto()
            .apply { userTokenWebSocketStorage.sendServerMessages(serverId, NEW_TEXT_MESSAGE, this, userId) }
    }

    @Operation(summary = "Обновить текстовое сообщение")
    @PutMapping
    suspend fun modifyTextMessage(
        @RequestBody textMessageDto: TextMessageDto,
        principal: Principal,
    ): TextMessageDto {
        val userId = tokenService.extractUserId(principal)
        val serverId = channelService.findChannel(textMessageDto.channelId).serverId

        // Обновить текстовое сообщение канала сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        return textMessageService.saveMessage(textMessageDto.toEntity(userId)).toDto()
            .apply { userTokenWebSocketStorage.sendServerMessages(serverId, EDIT_TEXT_MESSAGE, this, userId) }
    }

    @Operation(summary = "Удалить текстовое сообщение")
    @DeleteMapping("/{textMessageId}")
    suspend fun deleteTextMessage(@PathVariable textMessageId: UUID, principal: Principal) {
        val userId = tokenService.extractUserId(principal)
        val channelId = textMessageService.findMessage(textMessageId).channelId
        val serverId = channelService.findChannel(channelId).serverId

        // Удалить текстовое сообщение канала сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        textMessageService.deleteMessage(id = textMessageId, authorId = userId)
            .apply {
                userTokenWebSocketStorage.sendServerMessages(
                    serverId,
                    REMOVE_TEXT_MESSAGE,
                    textMessageId,
                    userId
                )
            }
    }
}