package tech.ruslo.starter.reactive.websocket.storage

import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono.just
import tech.ruslo.starter.reactive.websocket.event.WsSendMessage
import tools.jackson.databind.ObjectMapper

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
    private val sessions = ConcurrentHashMap<WebSocketSession, Long>()

    fun addSession(userId: Long, session: WebSocketSession) {
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