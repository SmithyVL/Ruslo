package ru.blimfy.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.web.reactive.socket.WebSocketSession
import ru.blimfy.websocket.WebSocketAutoConfigurationTest.TestConfig
import ru.blimfy.websocket.storage.WebSocketStorage

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @property webSocketStorage хранилище WebSocket сессий.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [WebSocketAutoConfiguration::class])
@Import(TestConfig::class)
class WebSocketAutoConfigurationTest {
    @Autowired
    private lateinit var webSocketStorage: WebSocketStorage<out Any>

    @Test
    fun `should load spring context`() {
        // Логика не требуется.
    }

    /**
     * Конфигурация для тестирования стартера.
     *
     * @author Владислав Кузнецов.
     * @since 0.0.1.
     */
    @TestConfiguration
    private class TestConfig {
        /**
         * Создаёт бин, который требуется стартеру для работоспособности. Нужен для тестов, потому что этот бин должен
         * создаваться извне стартера. Использует внутри [objectMapper].
         */
        @Bean
        fun tokenWebSocketStorage(objectMapper: ObjectMapper) =
            object : WebSocketStorage<Any>(objectMapper) {
                override fun addSession(token: String, userSession: WebSocketSession) {
                    // Логика не требуется.
                }

                override fun removeSession(token: String) {
                    // Логика не требуется.
                }
            }
    }
}