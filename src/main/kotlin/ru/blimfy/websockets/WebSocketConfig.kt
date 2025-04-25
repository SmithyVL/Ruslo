package ru.blimfy.websockets

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler

/**
 * Конфигурации для работы с WebSocket.
 *
 * @property webSocketHandler обработчик WebSocket соединений.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Configuration
class WebSocketConfig(private val webSocketHandler: WebSocketHandler) {
    /**
     * Возвращает настроенный бин обработчика сообщений WebSocket.
     */
    @Bean
    fun handlerMapping() =
        SimpleUrlHandlerMapping(mapOf("/{userId}" to webSocketHandler), 1)
}