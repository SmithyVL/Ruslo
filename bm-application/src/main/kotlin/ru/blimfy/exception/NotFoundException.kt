package ru.blimfy.exception

/**
 * Исключение для ситуаций, когда поиск должен увенчаться успехом, но этого не случилось.
 *
 * @property message сообщение об ошибке.
 * @property cause причина ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class NotFoundException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)