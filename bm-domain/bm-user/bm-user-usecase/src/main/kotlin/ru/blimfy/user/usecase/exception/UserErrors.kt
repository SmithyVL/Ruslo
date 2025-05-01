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
     * Пользователь не найден по идентификатору.
     */
    USER_BY_ID_NOT_FOUND("User with id - '%s', not found!"),

    /**
     * Пользователь не найден по имени пользователя.
     */
    USER_BY_USERNAME_NOT_FOUND("User with username - '%s', not found!"),

    /**
     * Пароль не найден по идентификатору пользователя.
     */
    PASSWORD_BY_USER_ID_NOT_FOUND("Password with user id - '%s', not found!"),
}