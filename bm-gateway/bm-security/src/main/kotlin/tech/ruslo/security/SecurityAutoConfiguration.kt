package tech.ruslo.security

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import tech.ruslo.common.factory.CustomConfigPropertiesReaderFactory
import tech.ruslo.security.config.properties.SecurityProperties

/**
 * Авто-конфигурация для подключения бинов для работы с безопасностью в приложении.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ComponentScan
@EnableConfigurationProperties(SecurityProperties::class)
@PropertySource("classpath:bm-security.yml", factory = CustomConfigPropertiesReaderFactory::class)
class SecurityAutoConfiguration