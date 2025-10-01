package tech.ruslo.starter.reactive.security

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.test.context.bean.override.mockito.MockitoBean
import tech.ruslo.starter.reactive.security.config.properties.SecurityProperties
import tech.ruslo.starter.reactive.security.properties.JwtProperties

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @property jwtProperties свойства для конфигурации JWT токенов.
 * @property securityProperties свойства для конфигурации стартера.
 * @property userDetailsService сервис для работы с авторизованным пользователем.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [ReactiveSecurityAutoConfiguration::class])
class ReactiveSecurityAutoConfigurationTest {
    @Autowired
    private lateinit var jwtProperties: JwtProperties

    @Autowired
    private lateinit var securityProperties: SecurityProperties

    @Suppress("unused")
    @MockitoBean
    lateinit var userDetailsService: ReactiveUserDetailsService

    @Test
    fun `should load spring context`() {
        assertEquals(jwtProperties.issuer.isNotEmpty(), true)
        assertEquals(jwtProperties.key.isNotEmpty(), true)
        assertEquals(securityProperties.permitAllPaths.isNotEmpty(), true)
    }
}