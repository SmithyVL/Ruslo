package ru.blimfy.common.enums

/**
 * Типы привилегий.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class PrivilegeTypes(val defaultGranted: Boolean) {
    /**
     * Чтение текстовых каналов.
     */
    TEXT_READ(true),

    /**
     * Отправка сообщений в текстовые каналы.
     */
    TEXT_WRITE(true),
}