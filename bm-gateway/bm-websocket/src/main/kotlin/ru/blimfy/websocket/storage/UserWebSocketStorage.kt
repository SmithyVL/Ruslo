package ru.blimfy.websocket.storage

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono.just
import ru.blimfy.websocket.dto.event.send.WsSendMessage

/**
 * Интерфейс для работы с WebSocket сессиями пользователей.
 *
 * @property objectMapper маппер для сериализации/десериализации объектов.
 * @property sessions WebSocket сессии.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class UserWebSocketStorage(private val objectMapper: ObjectMapper) {
    private val sessions = ConcurrentHashMap<WebSocketSession, UUID>()

    fun addSession(userId: UUID, session: WebSocketSession) {
        sessions[session] = userId
    }

    fun removeSession(session: WebSocketSession) {
        sessions.remove(session)
    }

    /**
     * Отправляет [type] сообщение с [data] во все активные сессии.
     */
    fun sendMessage(type: String, data: Any) {
        CoroutineScope(IO).launch {
            sessions.keys.forEach { session ->
                just(objectMapper.writeValueAsString(WsSendMessage(type, data)))
                    .map { session.textMessage(it) }
                    .let { session.send(it) }
                    .subscribe()
            }
        }
    }

    /**
     * Отправляет [message] в [session].
     */
    fun sendMessage(session: WebSocketSession, message: Any) {
        CoroutineScope(IO).launch {
            just(objectMapper.writeValueAsString(message))
                .map { session.textMessage(it) }
                .let { session.send(it) }
                .subscribe()
        }
    }
}