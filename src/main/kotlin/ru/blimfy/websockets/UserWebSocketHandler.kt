package ru.blimfy.websockets

import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import ru.blimfy.security.service.TokenService

/**
 * Обработчик пользовательских WebSocket соединений.
 *
 * @property userWebSocketService сервис для работы с WebSocket соединениями.
 * @property tokenService сервис для работы с JWT токенами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class UserWebSocketHandler(
    private val userWebSocketService: UserWebSocketService,
    private val tokenService: TokenService,
) : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> =
        session.receive()
            .doOnSubscribe {
                val userId = tokenService.extractUserId(session.handshakeInfo.headers)
                userWebSocketService.addUserSession(userId, session)
            }
            .doOnTerminate { userWebSocketService.removeUserSession(session) }
            .then()
}