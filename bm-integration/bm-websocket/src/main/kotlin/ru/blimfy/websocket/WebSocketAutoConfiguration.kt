package ru.blimfy.websocket

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.ComponentScan

/**
 * Авто-конфигурация для подключения бинов для работы с WebSocket сессиями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration
@ComponentScan
class WebSocketAutoConfiguration