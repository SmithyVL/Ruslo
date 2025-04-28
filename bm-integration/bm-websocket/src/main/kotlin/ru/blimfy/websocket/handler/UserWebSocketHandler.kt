package ru.blimfy.websocket.handler

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import ru.blimfy.websocket.service.WebSocketService

/**
 * Обработчик пользовательских WebSocket соединений.
 *
 * @property webSocketService сервис для работы с WebSocket соединениями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
internal class UserWebSocketHandler(private val webSocketService: WebSocketService) : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        // Получаем из заголовков WebSocket сессии токен авторизации пользователя, который является обязательным
        // параметром для установления соединения.
        val token = extractToken(session.handshakeInfo.headers)

        return session.receive()
            .doOnSubscribe { webSocketService.addSession(token!!, session) }
            .doOnTerminate { webSocketService.removeSession(token!!) }
            .then()
    }

    /**
     * Возвращает JWT токен из [headers] или null, если его нет.
     */
    private fun extractToken(headers: HttpHeaders) =
        headers.getFirst("Authorization")?.substringAfter("Bearer ")
}