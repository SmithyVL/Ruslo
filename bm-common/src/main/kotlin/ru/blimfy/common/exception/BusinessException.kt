package ru.blimfy.common.exception

/**
 * Предок всех бизнес-исключений.
 *
 * @param message описание ошибки.
 * @param throwable причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
open class BusinessException(
    message: String? = null,
    throwable: Throwable? = null,
) : RuntimeException(message, throwable)