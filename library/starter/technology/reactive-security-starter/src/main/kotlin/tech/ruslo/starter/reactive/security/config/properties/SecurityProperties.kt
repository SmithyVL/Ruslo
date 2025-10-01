package tech.ruslo.starter.reactive.security.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Свойства конфигурации стартера безопасности.
 *
 * @property permitAllPaths пути API, которые полностью открыты для доступа извне.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@ConfigurationProperties("security")
data class SecurityProperties(val permitAllPaths: List<String>)