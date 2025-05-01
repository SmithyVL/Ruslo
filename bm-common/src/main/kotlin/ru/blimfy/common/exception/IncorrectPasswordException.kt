package ru.blimfy.common.exception

/**
 * Введён неправильный пароль.
 *
 * @param message сообщение об ошибке.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class IncorrectPasswordException(message: String? = null, cause: Throwable? = null) : BusinessException(message, cause)