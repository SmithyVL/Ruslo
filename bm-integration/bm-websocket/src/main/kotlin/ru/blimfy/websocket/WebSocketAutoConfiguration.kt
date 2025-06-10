package ru.blimfy.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

/**
 * Авто-конфигурация для подключения бинов для работы с WebSocket сессиями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ComponentScan
class WebSocketAutoConfiguration {
    /**
     * Создаёт бин, отвечающего за маппинг JSON в объекты и наоборот.
     */
    @Bean
    @ConditionalOnMissingBean
    fun objectMapper() = ObjectMapper()
}