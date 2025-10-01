package tech.ruslo.starter.client.user

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.web.reactive.function.client.WebClient.create
import tech.ruslo.yml.expander.CustomConfigPropertiesReaderFactory

/**
 * Авто-конфигурация для подключения бинов для работы с клиентом сервиса пользователей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ComponentScan
@PropertySource("classpath:user-client.yml", factory = CustomConfigPropertiesReaderFactory::class)
class UserClientAutoConfiguration {
    @Value($$"${ruslo.user-service.client.url}")
    private lateinit var userServiceUrl: String

    @Bean
    fun userWebClient() = create(userServiceUrl)
}