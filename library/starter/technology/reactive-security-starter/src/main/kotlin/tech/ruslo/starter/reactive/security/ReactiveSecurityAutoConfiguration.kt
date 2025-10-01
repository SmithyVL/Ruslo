package tech.ruslo.starter.reactive.security

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import tech.ruslo.starter.reactive.security.config.properties.SecurityProperties
import tech.ruslo.starter.reactive.security.properties.JwtProperties
import tech.ruslo.yml.expander.CustomConfigPropertiesReaderFactory

/**
 * Авто-конфигурация для подключения бинов для работы с реактивной безопасностью в приложении.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ComponentScan
@EnableConfigurationProperties(SecurityProperties::class, JwtProperties::class)
@PropertySource("classpath:reactive-security.yml", factory = CustomConfigPropertiesReaderFactory::class)
class ReactiveSecurityAutoConfiguration