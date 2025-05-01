package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.data.domain.PageRequest.of
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.NewChannelDto
import ru.blimfy.gateway.dto.channel.toDto
import ru.blimfy.gateway.dto.channel.toEntity
import ru.blimfy.gateway.dto.message.text.TextMessageDto
import ru.blimfy.gateway.dto.message.text.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.channel.ChannelService
import ru.blimfy.server.usecase.message.TextMessageService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_SERVER_CHANNEL
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_SERVER_CHANNEL
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_SERVER_CHANNEL

/**
 * Контроллер для работы с информацией о каналах.
 *
 * @property channelService сервис для работы с каналами.
 * @property textMessageService сервис для работы с сообщениями каналов.
 * @property serverService сервис для работы с серверами.
 * @property tokenService сервис для работы с токенами.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ChannelController", description = "Контроллер для работы с каналами серверов")
@RestController
@RequestMapping("/v1/channels")
class ChannelController(
    private val channelService: ChannelService,
    private val textMessageService: TextMessageService,
    private val serverService: ServerService,
    private val tokenService: TokenService,
    private val userWebSocketStorage: UserWebSocketStorage,
) {
    @Operation(summary = "Создать канал сервера")
    @PostMapping
    suspend fun createChannel(@RequestBody newChannelDto: NewChannelDto, principal: Principal): ChannelDto {
        val userId = tokenService.extractUserId(principal)

        // Создать канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = newChannelDto.serverId, userId = userId)

        return channelService.saveChannel(newChannelDto.toEntity()).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, NEW_SERVER_CHANNEL, this, userId) }
    }

    @Operation(summary = "Обновить канал сервера")
    @PutMapping
    suspend fun modifyChannel(@RequestBody channelDto: ChannelDto, principal: Principal): ChannelDto {
        val userId = tokenService.extractUserId(principal)

        // Обновить канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = channelDto.serverId, userId = userId)

        return channelService.saveChannel(channelDto.toEntity()).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, EDIT_SERVER_CHANNEL, this, userId) }
    }

    @Operation(summary = "Удалить канал по его идентификатору")
    @DeleteMapping("/{channelId}")
    suspend fun deleteChannel(@PathVariable channelId: UUID, principal: Principal) {
        val userId = tokenService.extractUserId(principal)
        val serverId = channelService.findChannel(channelId).serverId

        // Удалить канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        channelService.deleteChannel(channelId)
            .apply { userWebSocketStorage.sendServerMessages(serverId, REMOVE_SERVER_CHANNEL, channelId, userId) }
    }

    @Operation(summary = "Получить канал по его идентификатору")
    @GetMapping("/{channelId}")
    suspend fun findChannel(@PathVariable channelId: UUID, principal: Principal) =
        channelService.findChannel(channelId).toDto().apply {
            // Получить информацию о канале сервера может только его участник.
            serverService.checkServerViewAccess(serverId = serverId, userId = tokenService.extractUserId(principal))
        }

    @Operation(summary = "Получить страницу с сообщениями канала")
    @GetMapping("/{channelId}/messages")
    suspend fun findChannelMessages(
        @PathVariable channelId: UUID,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int,
        principal: Principal,
    ): Flow<TextMessageDto> {
        // Получить текстовые сообщения канала сервера может только его участник.
        serverService.checkServerViewAccess(
            serverId = channelService.findChannel(channelId).serverId,
            userId = tokenService.extractUserId(principal),
        )

        return textMessageService.findPageChannelMessages(channelId, of(pageNumber, pageSize)).map { it.toDto() }
    }
}