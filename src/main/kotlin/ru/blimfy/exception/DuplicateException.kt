package ru.blimfy.exception

/**
 * Исключение для ситуаций, когда поиск не должен ничего найти, а нашёл.
 *
 * @property message сообщение об ошибке.
 * @property cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class DuplicateException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)