package tech.ruslo.gateway.access.control.exception

/**
 * Сообщения ошибок в шлюзе API.
 *
 * @param msg текст ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class AccessErrors(val msg: String) {
    /**
     * Неверный пароль.
     */
    INCORRECT_PASSWORD("Incorrect password - '%s'!"),

    /**
     * Попытка выполнить запрещённое действие над сервером.
     */
    SERVER_ACCESS_DENIED("You don't have access to this action on the server (\"%s\")!"),

    /**
     * Доступ к просмотру сервера запрещён.
     */
    SERVER_VIEW_ACCESS_DENIED("You don't have access to view your server (\"%s\")!"),

    /**
     * Доступ к просмотру личного канала запрещён.
     */
    DM_VIEW_ACCESS_DENIED("You don't have access to view your dm channel (\"%s\")!"),

    /**
     * Попытка выполнить запрещённое действие над группой.
     */
    GROUP_ACCESS_DENIED("You don't have access to this action on the group (\"%s\")!"),
}