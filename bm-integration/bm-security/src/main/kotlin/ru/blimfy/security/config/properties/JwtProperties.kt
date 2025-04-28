package ru.blimfy.security.config.properties

/**
 * Свойства конфигурации генерации JWT токенов.
 *
 * @property issuer кто выдаёт токены.
 * @property key ключ для подписания токена.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class JwtProperties(val issuer: String, val key: String)