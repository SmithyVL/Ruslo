package tech.ruslo.websocket.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Свойства конфигурации парсинга JWT токенов.
 *
 * @property key ключ подписи токена.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@ConfigurationProperties("bm-websocket.jwt")
data class JwtProperties(val key: String)