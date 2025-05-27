package ru.blimfy.websocket.handler

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
    override fun handle(session: WebSocketSession): Mono<Void> = session
        .receive()
        .doOnNext {
            // Получаем первым сообщением токен авторизации для сохранения WebSocket сессии.
            webSocketService.addSession(it.payloadAsText, session)
        }
        .doOnTerminate { webSocketService.removeSession(session) }
        .then()
}