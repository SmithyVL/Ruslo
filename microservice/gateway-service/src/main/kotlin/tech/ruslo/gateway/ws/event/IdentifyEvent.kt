package tech.ruslo.gateway.ws.event

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import tech.ruslo.gateway.ws.dto.ShortServerDto
import tech.ruslo.gateway.ws.dto.ShortUserDto
import tech.ruslo.gateway.ws.dto.event.read.IdentifyDto
import tech.ruslo.gateway.ws.dto.event.send.ReadyDto
import tech.ruslo.server.dto.ServerClient
import tech.ruslo.server.dto.ServerDto
import tech.ruslo.starter.reactive.security.service.TokenService
import tech.ruslo.starter.reactive.websocket.event.base.BaseEvent
import tech.ruslo.starter.reactive.websocket.storage.UserWebSocketStorage
import tech.ruslo.user.dto.UserClient
import tech.ruslo.user.dto.UserDto
import tools.jackson.databind.ObjectMapper

/**
 * Обработчик идентифицирующего события.
 *
 * @property wsStorage хранилище WebSocket сессий.
 * @property tokenService сервис для работы с токенами.
 * @property userClient клиент для работы с сервисом пользователей.
 * @property serverClient клиент для работы с сервисом серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class IdentifyEvent(
    private val wsStorage: UserWebSocketStorage,
    private val tokenService: TokenService,
    private val userClient: UserClient,
    private val serverClient: ServerClient,
    private val objectMapper: ObjectMapper,
) : BaseEvent {
    override suspend fun match(message: String) = try {
        objectMapper.readValue(message, IdentifyDto::class.java)
        true
    } catch (_: Exception) {
        false
    }

    override suspend fun process(session: WebSocketSession, message: String) {
        val payload = objectMapper.readValue(message, IdentifyDto::class.java)
        val userId = tokenService.extractUserId(payload.token)
        wsStorage.addSession(userId, session)

        val user = userClient.getUser(userId).toShortDto()
        val servers = serverClient.getUserServers(userId).map { it.toShortDto(userId) }.toList()

        wsStorage.sendMessage(session, ReadyDto(user, servers))
    }
}

/**
 * Возвращает краткое DTO пользователя из полного DTO представления пользователя.
 */
fun UserDto.toShortDto() = ShortUserDto(id!!, username)

/**
 * Возвращает краткое DTO пользователя из полного DTO представления пользователя.
 */
fun ServerDto.toShortDto(userId: Long) = ShortServerDto(id!!, name, userId == ownerId)