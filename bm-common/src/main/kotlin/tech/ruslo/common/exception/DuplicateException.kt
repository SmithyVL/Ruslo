package tech.ruslo.common.exception

/**
 * Найден дубликат.
 *
 * @param message описание ошибки.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class DuplicateException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)