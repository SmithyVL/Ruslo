package tech.ruslo.starter.client.channel

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
@SpringBootTest(classes = [ChannelClientAutoConfiguration::class])
class ChannelClientAutoConfigurationTests {
    @Value($$"${ruslo.channel-service.client.url}")
    private lateinit var channelServiceUrl: String

    @Test
    fun `context loads`() {
        assertEquals(channelServiceUrl, "http://localhost:8081")
    }
}