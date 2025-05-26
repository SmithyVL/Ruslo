package ru.blimfy.gateway.exception

/**
 * Сообщения ошибок в шлюзе API.
 *
 * @param msg текст ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class GatewayErrors(val msg: String) {
    /**
     * Неверный пароль.
     */
    INCORRECT_PASSWORD("Incorrect password - '%s'!"),

    /**
     * Попытка удаления участника сервера, который является его владельцем.
     */
    INCORRECT_LEAVING_SERVER("You cannot leave the server ('%s') as its owner!"),
}