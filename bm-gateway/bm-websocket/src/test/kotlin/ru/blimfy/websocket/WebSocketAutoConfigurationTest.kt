package ru.blimfy.websocket

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [WebSocketAutoConfiguration::class], webEnvironment = NONE)
class WebSocketAutoConfigurationTest {
    @Test
    fun `should load spring context`() {
        // Логика не требуется.
    }
}