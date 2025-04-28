package ru.blimfy.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import ru.blimfy.websocket.storage.WebSocketStorage

/**
 * Авто-конфигурация для подключения бинов для работы с WebSocket сессиями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ConditionalOnBean(WebSocketStorage::class)
class WebSocketAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun objectMapper(): ObjectMapper =
        ObjectMapper().configure(WRITE_DATES_AS_TIMESTAMPS, false).registerModule(JavaTimeModule())
}