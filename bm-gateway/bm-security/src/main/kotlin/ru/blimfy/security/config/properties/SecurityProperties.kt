package ru.blimfy.security.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Свойства конфигурации стартера безопасности.
 *
 * @property jwt конфигурации токенов.
 * @property permitAllPaths пути API, которые полностью открыты для доступа извне.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@ConfigurationProperties("bm-security")
data class SecurityProperties(val jwt: JwtProperties, val permitAllPaths: List<String> = emptyList())