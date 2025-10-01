package tech.ruslo.gateway.ws.dto.enums

/**
 * События, получаемые через WebSocket соединения.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ReceiveEvents {
    /**
     * Выполняет первоначальное рукопожатие с WebSocket сервером.
     */
    IDENTIFY,
}