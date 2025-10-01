package tech.ruslo.exceptions.core

/**
 * Что-то не найдено.
 *
 * @param message сообщение об ошибке.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class NotFoundException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)