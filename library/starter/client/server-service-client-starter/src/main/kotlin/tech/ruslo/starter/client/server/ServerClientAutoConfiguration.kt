package tech.ruslo.starter.client.server

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.web.reactive.function.client.WebClient.create
import tech.ruslo.yml.expander.CustomConfigPropertiesReaderFactory

/**
 * Авто-конфигурация для подключения бинов для работы с клиентом сервиса серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ComponentScan
@PropertySource("classpath:server-client.yml", factory = CustomConfigPropertiesReaderFactory::class)
class ServerClientAutoConfiguration {
    @Value($$"${ruslo.server-service.client.url}")
    private lateinit var serverServiceUrl: String

    @Bean
    fun serverWebClient() = create(serverServiceUrl)
}