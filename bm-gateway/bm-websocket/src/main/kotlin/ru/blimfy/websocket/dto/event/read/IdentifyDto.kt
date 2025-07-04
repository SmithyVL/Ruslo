package ru.blimfy.websocket.dto.event.read

import ru.blimfy.websocket.dto.enums.ReceiveEvents.IDENTIFY

/**
 * Информация об идентификации соединения.
 *
 * @property token токен авторизации.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class IdentifyDto() : ReadEvent {
    lateinit var token: String

    override var type = IDENTIFY.name
}