package tech.ruslo.server.usecase.exception

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

    /**
     * Стандартная роль не найдена.
     */
    DEFAULT_ROLE_NOT_FOUND("Default role in server (%s) not found!"),

    /**
     * Роль не найдена.
     */
    ROLE_NOT_FOUND("Role (%s) in server (%s) not found!"),

    /**
     * Участник сервера не найден.
     */
    MEMBER_NOT_FOUND("Member not found!"),
}