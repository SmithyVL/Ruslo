package ru.blimfy.common.exception

/**
 * Получены некорректные данные.
 *
 * @param message сообщение об ошибке.
 * @param cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class IncorrectDataException(message: String? = null, cause: Throwable? = null) : BusinessException(message, cause)