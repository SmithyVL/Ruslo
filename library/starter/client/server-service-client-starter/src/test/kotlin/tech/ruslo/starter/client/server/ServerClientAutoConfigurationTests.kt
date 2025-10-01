package tech.ruslo.starter.client.server

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [ServerClientAutoConfiguration::class])
class ServerClientAutoConfigurationTests {
    @Value($$"${ruslo.server-service.client.url}")
    private lateinit var serverServiceUrl: String

    @Test
    fun `context loads`() {
        assertEquals(serverServiceUrl, "http://localhost:8082")
    }
}