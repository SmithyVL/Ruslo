package ru.blimfy.websocket.dto

/**
 * Сообщение для отправки через WebSocket соединения.
 *
 * @property type тип сообщения.
 * @property data содержимое сообщения.
 * @property extra дополнительная информация.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class WsMessage(val type: WsMessageTypes, val data: Any, val extra: Any? = null)