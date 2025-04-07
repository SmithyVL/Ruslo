package ru.blimfy.security

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Свойства конфигурации генерации JWT токенов.
 *
 * @property issuer кто выдаёт токены.
 * @property key ключ для подписания токена.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@ConfigurationProperties("jwt")
data class JwtProperties(val issuer: String, val key: String)
