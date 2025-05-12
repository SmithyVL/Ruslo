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
    INCORRECT_PASSWORD("Incorrect password - '%s'!"),
}