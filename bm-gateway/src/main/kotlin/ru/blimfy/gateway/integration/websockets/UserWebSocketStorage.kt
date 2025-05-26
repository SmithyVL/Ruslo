package ru.blimfy.gateway.integration.websockets

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import ru.blimfy.security.service.TokenService
import ru.blimfy.websocket.storage.WebSocketStorage

/**
 * Класс-хранилище WebSocket сессий для пользователей.
 *
 * @param objectMapper маппер для сериализации/десериализации объектов.
 * @property tokenService сервис для работы с JWT токенами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class UserWebSocketStorage(
    objectMapper: ObjectMapper,
    private val tokenService: TokenService,
) : WebSocketStorage<UUID>(objectMapper) {
    override fun addSession(token: String, session: WebSocketSession) {
        sessions[tokenService.extractUserId(token)] = session
    }

    override fun removeSession(userId: UUID) {
        sessions.remove(userId)
    }
}