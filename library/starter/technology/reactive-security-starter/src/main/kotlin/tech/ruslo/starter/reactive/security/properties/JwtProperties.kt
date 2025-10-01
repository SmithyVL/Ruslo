package tech.ruslo.starter.reactive.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Свойства конфигурации JWT токенов.
 *
 * @property issuer кто выдаёт токены.
 * @property key ключ для подписания токена.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@ConfigurationProperties("jwt")
data class JwtProperties(val issuer: String, val key: String)