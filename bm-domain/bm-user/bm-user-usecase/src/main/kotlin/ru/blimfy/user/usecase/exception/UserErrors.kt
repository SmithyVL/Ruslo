package ru.blimfy.user.usecase.exception

/**
 * Сообщения ошибок работы с пользователями и их паролями.
 *
 * @param msg текст ошибка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class UserErrors(val msg: String) {
    /**
     * Пользователь уже существует.
     */
    USER_ALREADY_EXISTS("User with username - '%s', is exists!"),

    /**
     * Пользователь не найден.
     */
    USER_NOT_FOUND("User (%s), not found!"),

    /**
     * Пароль пользователя не найден.
     */
    PASSWORD_NOT_FOUND("Password for user (%s), not found!"),
}