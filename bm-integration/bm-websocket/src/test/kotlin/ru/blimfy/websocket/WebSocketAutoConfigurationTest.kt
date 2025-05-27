package ru.blimfy.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.web.reactive.socket.WebSocketSession
import ru.blimfy.websocket.WebSocketAutoConfigurationTest.TestConfig
import ru.blimfy.websocket.storage.WebSocketStorage

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [WebSocketAutoConfiguration::class], webEnvironment = NONE)
@Import(TestConfig::class)
class WebSocketAutoConfigurationTest {
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
         * создаваться извне стартера.
         */
        @Bean
        fun tokenWebSocketStorage() =
            object : WebSocketStorage<Any>(ObjectMapper()) {
                override fun addSession(token: String, session: WebSocketSession) {
                    // Логика не требуется.
                }

                override fun removeSession(session: WebSocketSession) {
                    // Логика не требуется.
                }
            }
    }
}