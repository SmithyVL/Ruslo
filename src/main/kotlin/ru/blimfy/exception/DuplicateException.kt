package ru.blimfy.exception

/**
 * Исключение для ситуаций, когда при создании сущности найден дубликат.
 *
 * @property message сообщение об ошибке.
 * @property cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class DuplicateException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)