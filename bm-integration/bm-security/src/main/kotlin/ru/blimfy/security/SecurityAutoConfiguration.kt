package ru.blimfy.security

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.PropertySource
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import ru.blimfy.security.factory.CustomConfigPropertiesReaderFactory

/**
 * Авто-конфигурация для подключения бинов для работы с безопасностью в приложении.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ConfigurationPropertiesScan
@PropertySource("classpath:bm-security.yml", factory = CustomConfigPropertiesReaderFactory::class)
@ConditionalOnBean(ReactiveUserDetailsService::class)
class SecurityAutoConfiguration