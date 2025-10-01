package tech.ruslo.starter.reactive.websocket.handler

import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.scheduler.Schedulers.boundedElastic
import tech.ruslo.starter.reactive.websocket.event.base.BaseEvent
import tech.ruslo.starter.reactive.websocket.storage.UserWebSocketStorage

/**
 * Обработчик WebSocket соединений.
 *
 * @property wsStorage хранилище WebSocket соединений.
 * @property events события обработки сообщений.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
internal class WebSocketHandler(
    private val wsStorage: UserWebSocketStorage,
    private val events: Set<BaseEvent>,
) : WebSocketHandler {
    override fun handle(session: WebSocketSession) = session
        .receive()
        .map { it.payloadAsText }
        .publishOn(boundedElastic())
        .flatMap { message ->
            mono {
                events
                    .filter { event -> event.match(message) }
                    .forEach { event -> event.process(session, message) }
            }
        }
        .doOnTerminate { wsStorage.removeSession(session) }
        .then()
}