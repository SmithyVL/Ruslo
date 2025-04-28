package ru.blimfy.websocket.storage

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.concurrent.ConcurrentHashMap
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono.just
import ru.blimfy.websocket.dto.WsMessage
import ru.blimfy.websocket.dto.WsMessageTypes
import ru.blimfy.websocket.service.WebSocketService

/**
 * Интерфейс для работы с WebSocket сессиями.
 *
 * @property objectMapper маппер для сериализации/десериализации объектов.
 * @property sessions WebSocket сессии.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
abstract class WebSocketStorage<SESSION_KEY>(private val objectMapper: ObjectMapper): WebSocketService {
    protected val sessions = ConcurrentHashMap<SESSION_KEY, WebSocketSession>()

    abstract override fun addSession(token: String, session: WebSocketSession)

    abstract override fun removeSession(token: String)

    /**
     * Отправляет [type] сообщение с [data] в сессию с [key].
     */
    protected fun sendMessage(key: SESSION_KEY, type: WsMessageTypes, data: Any) {
        sessions[key]?.let { session ->
            session
                .send(just(session.textMessage(objectMapper.writeValueAsString(WsMessage(type, data)))))
                .subscribe()
        }
    }
}