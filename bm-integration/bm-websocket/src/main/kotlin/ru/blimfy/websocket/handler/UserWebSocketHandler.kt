package ru.blimfy.websocket.handler

import java.util.UUID.fromString
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
        .doOnTerminate {
            // Получаем из URL идентификатор пользователя и удаляем WebSocket сессию.
            webSocketService.removeSession(findUserIdFromWsUrl(session))
        }
        .then()

    /**
     * Возвращает идентификатор пользователя, открывшего [session] через WebSocket из информации о "рукопожатии"
     * (handshakeInfo).
     */
    private fun findUserIdFromWsUrl(session: WebSocketSession) =
        fromString(PATTERN_USER_ID.findAll(session.handshakeInfo.uri.toString()).first().value)

    private companion object {
        /**
         * REGEX для поиска идентификатора пользователя в URL соединения через WebSocket.
         */
        val PATTERN_USER_ID = Regex("[^/]+$")
    }
}