package tech.ruslo.server.exception

/**
 * Сообщения ошибок при работе с серверами.
 *
 * @param msg текст ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ServerErrors(val msg: String) {
    /**
     * Сервер не найден.
     */
    SERVER_NOT_FOUND("Server (%s) not found!"),
}