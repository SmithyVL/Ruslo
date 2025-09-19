package tech.ruslo.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import tech.ruslo.common.factory.CustomConfigPropertiesReaderFactory
import tech.ruslo.websocket.config.properties.JwtProperties

/**
 * Авто-конфигурация для подключения бинов для работы с WebSocket сессиями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ComponentScan
@EnableConfigurationProperties(JwtProperties::class)
@PropertySource("classpath:bm-websocket.yml", factory = CustomConfigPropertiesReaderFactory::class)
class WebSocketAutoConfiguration {
    /**
     * Создаёт бин, отвечающего за маппинг JSON в объекты и наоборот.
     */
    @Bean
    @ConditionalOnMissingBean
    fun objectMapper() = ObjectMapper()
}