package tech.ruslo.exceptions.core

/**
 * Найден дубликат
 *
 * @param message сообщение об ошибке.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class DuplicateException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)