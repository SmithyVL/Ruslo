package ru.blimfy.websocket.event.base

import org.springframework.web.reactive.socket.WebSocketSession
import ru.blimfy.websocket.dto.enums.ReceiveEvents
import ru.blimfy.websocket.dto.event.read.ReadEvent

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