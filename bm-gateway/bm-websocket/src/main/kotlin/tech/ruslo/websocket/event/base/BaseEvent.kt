package tech.ruslo.websocket.event.base

import org.springframework.web.reactive.socket.WebSocketSession
import tech.ruslo.websocket.dto.enums.ReceiveEvents
import tech.ruslo.websocket.dto.event.read.ReadEvent

/**
 * Базовый класс для работы с WebSocket событиями.
 *
 * @property type тип события.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface BaseEvent {
    val type: ReceiveEvents

    /**
     * Обработка [data] из [session] сообщения.
     */
    suspend fun process(session: WebSocketSession, data: ReadEvent)
}