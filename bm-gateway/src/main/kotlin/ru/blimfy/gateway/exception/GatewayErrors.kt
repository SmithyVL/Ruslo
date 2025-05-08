package ru.blimfy.gateway.exception

/**
 * Сообщения ошибок в шлюзе API.
 *
 * @param msg текст ошибка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class GatewayErrors(val msg: String) {
    /**
     * Неверный пароль.
     */
    INCORRECT_PASSWORD("Incorrect password - '%s', for %s!"),

    /**
     * Приглашение является невалидным для сервера.
     */
    INVALID_INVITE("Incorrect invite with id - '%s', for server with id - '%s!"),

    /**
     * Несоответствие канала серверу.
     */
    INVALID_CHANNEL("Incorrect channel with id - '%s', for server with id - '%s!"),
}