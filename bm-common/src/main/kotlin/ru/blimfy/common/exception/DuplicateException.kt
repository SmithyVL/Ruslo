package ru.blimfy.common.exception

/**
 * Найден дубликат при создании объекта.
 *
 * @param message сообщение об ошибке.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class DuplicateException(message: String? = null, cause: Throwable? = null) : BusinessException(message, cause)