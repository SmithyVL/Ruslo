package tech.ruslo.user.exception

/**
 * Сообщения ошибок работы с пользователями и их паролями.
 *
 * @param msg текст ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class UserErrors(val msg: String) {
    /**
     * Пользователь уже существует.
     */
    USER_ALREADY_EXISTS("User (%s) is exists!"),

    /**
     * Пользователь не найден.
     */
    USER_NOT_FOUND("User (%s) not found!"),
}