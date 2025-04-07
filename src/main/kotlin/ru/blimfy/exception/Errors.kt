package ru.blimfy.exception

/**
 * Сообщения ошибок.
 *
 * @param msg текст ошибка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class Errors(val msg: String) {
    /**
     * Пользователь уже существует.
     */
    USER_ALREADY_EXISTS("User with username - ['%s'], is exists!"),

    /**
     * Неверный пароль.
     */
    INCORRECT_PASSWORD("Incorrect password - ['%s'], for %s!"),

    /**
     * Пользователь не найден по идентификатору.
     */
    USER_BY_ID_NOT_FOUND("User with id - ['%s'], not found!"),

    /**
     * Пользователь не найден по имени пользователя.
     */
    USER_BY_USERNAME_NOT_FOUND("User with username - ['%s'], not found!"),

    /**
     * Сервер не найден по идентификатору.
     */
    SERVER_BY_ID_NOT_FOUND("Server with id - ['%s'], not found!"),

    /**
     * Канал сервера не найден по идентификатору.
     */
    CHANNEL_BY_ID_NOT_FOUND("Channel with id - ['%s'], not found!"),

    /**
     * Приглашение на сервер не найдено по идентификатору.
     */
    INVITE_BY_ID_NOT_FOUND("Invite with id - ['%s'], not found!"),

    /**
     * Участник сервера не найден по идентификатору пользователя и сервера.
     */
    MEMBER_BY_USER_ID_AND_SERVER_ID_NOT_FOUND("Member with user id - ['%s'], and server id - ['%s'], not found!"),

    /**
     * Пароль не найден по идентификатору пользователя.
     */
    PASSWORD_BY_USER_ID_NOT_FOUND("Password with user id - ['%s'], not found!"),
}