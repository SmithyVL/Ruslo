package tech.ruslo.starter.reactive.websocket.event.base

import org.springframework.web.reactive.socket.WebSocketSession

/**
 * Базовый класс для работы с WebSocket событиями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface BaseEvent {
    /**
     * Проверяет нужно ли выполнять обработку [message] для этого события.
     */
    suspend fun match(message: String): Boolean

    /**
     * Обработка [message] из [session].
     */
    suspend fun process(session: WebSocketSession, message: String)
}