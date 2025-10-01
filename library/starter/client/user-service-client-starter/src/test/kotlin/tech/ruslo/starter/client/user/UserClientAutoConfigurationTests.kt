package tech.ruslo.starter.client.user

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
@SpringBootTest(classes = [UserClientAutoConfiguration::class])
class UserClientAutoConfigurationTests {
    @Value($$"${ruslo.user-service.client.url}")
    private lateinit var userServiceUrl: String

    @Test
    fun `context loads`() {
        assertEquals(userServiceUrl, "http://localhost:8080")
    }
}