package ru.blimfy.common.exception

/**
 * Не найдено искомое.
 *
 * @param message сообщение об ошибке.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class NotFoundException(message: String? = null, cause: Throwable? = null) : BusinessException(message, cause)