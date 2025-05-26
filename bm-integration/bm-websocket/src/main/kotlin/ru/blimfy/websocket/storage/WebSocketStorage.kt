package ru.blimfy.websocket.storage

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
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

    abstract override fun removeSession(userId: UUID)

    /**
     * Отправляет [type] сообщение с [data] во все активные сессии.
     */
    fun sendMessage(type: WsMessageTypes, data: Any, extra: Any? = null) {
        sessions.values.forEach {
            it.send(just(it.textMessage(objectMapper.writeValueAsString(WsMessage(type, data, extra))))).subscribe()
        }
    }
}