package tech.ruslo.starter.reactive.websocket

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import tools.jackson.databind.ObjectMapper

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [ReactiveWebsocketAutoConfiguration::class])
class ReactiveWebsocketAutoConfigurationTest {
    @Suppress("unused")
    @MockitoBean
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `should load spring context`() {
        // Логика не требуется.
    }
}