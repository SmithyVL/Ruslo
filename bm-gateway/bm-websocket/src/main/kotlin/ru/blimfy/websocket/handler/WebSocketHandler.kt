package ru.blimfy.websocket.handler

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import ru.blimfy.websocket.dto.enums.ReceiveEvents.valueOf as receiveEventsValueOf
import ru.blimfy.websocket.dto.event.read.ReadEvent
import ru.blimfy.websocket.event.base.BaseEvent
import ru.blimfy.websocket.storage.UserWebSocketStorage

/**
 * Обработчик WebSocket соединений.
 *
 * @property wsStorage хранилище WebSocket соединений.
 * @property objectMapper маппер для работы с JSON.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
internal class WebSocketHandler(
    private val wsStorage: UserWebSocketStorage,
    private val events: List<BaseEvent>,
    private val objectMapper: ObjectMapper,
) : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> = session
        .receive()
        .map {
            objectMapper.readValue(it.payloadAsText, ReadEvent::class.java)
        }
        .doOnNext {
            runBlocking {
                events
                    .firstOrNull { event -> event.type == receiveEventsValueOf(it.type) }
                    ?.process(session, it)
            }
        }
        .doOnTerminate { wsStorage.removeSession(session) }
        .then()
}