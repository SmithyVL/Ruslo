package ru.blimfy.common.exception

/**
 * Нет доступа к ресурсу.
 *
 * @param message сообщение об ошибке.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class AccessDeniedException(message: String? = null, cause: Throwable? = null) : BusinessException(message, cause)