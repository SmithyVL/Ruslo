package tech.ruslo.websocket

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.test.context.bean.override.mockito.MockitoBean
import tech.ruslo.domain.server.usecase.server.ServerService
import tech.ruslo.domain.user.usecase.user.UserService
import tech.ruslo.gateway.mapper.ServerMapper

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [WebSocketAutoConfiguration::class], webEnvironment = NONE)
class WebSocketAutoConfigurationTest {
    @Suppress("unused")
    @MockitoBean
    lateinit var userService: UserService

    @Suppress("unused")
    @MockitoBean
    lateinit var serverService: ServerService

    @Suppress("unused")
    @MockitoBean
    lateinit var serverMapper: ServerMapper

    @Test
    fun `should load spring context`() {
        // Логика не требуется.
    }
}