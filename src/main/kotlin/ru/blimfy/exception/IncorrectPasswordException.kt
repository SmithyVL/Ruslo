package ru.blimfy.exception

/**
 * Исключение для ситуаций, когда пароль не совпал с существующим.
 *
 * @property message сообщение об ошибке.
 * @property cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class IncorrectPasswordException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)