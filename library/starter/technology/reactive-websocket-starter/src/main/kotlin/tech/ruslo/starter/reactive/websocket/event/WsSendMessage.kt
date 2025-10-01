package tech.ruslo.starter.reactive.websocket.event

/**
 * Сообщение для отправки через WebSocket соединения.
 *
 * @property type тип сообщения.
 * @property data содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class WsSendMessage(val type: String, val data: Any)