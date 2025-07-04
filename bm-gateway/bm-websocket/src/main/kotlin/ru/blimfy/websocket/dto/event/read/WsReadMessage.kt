package ru.blimfy.websocket.dto.event.read

/**
 * Сообщение для получения через WebSocket соединения.
 *
 * @property type тип сообщения.
 * @property data содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class WsReadMessage(val type: String, val data: ReadEvent)