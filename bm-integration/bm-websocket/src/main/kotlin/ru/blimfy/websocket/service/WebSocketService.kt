package ru.blimfy.websocket.service

import java.util.UUID
import org.springframework.web.reactive.socket.WebSocketSession

/**
 * Интерфейс для работы с WebSocket сессиями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
internal interface WebSocketService {
    /**
     * Добавляет новую WebSocket [session] по [token].
     */
    fun addSession(token: String, session: WebSocketSession)

    /**
     * Удаляет существующую WebSocket сессию для пользователя с [userId].
     */
    fun removeSession(userId: UUID)
}