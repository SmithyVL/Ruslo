package tech.ruslo.gateway.ws.dto.event.read

import tech.ruslo.gateway.ws.dto.enums.ReceiveEvents.IDENTIFY

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