package tech.ruslo.security

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.test.context.bean.override.mockito.MockitoBean
import tech.ruslo.security.SecurityAutoConfigurationTest.TestConfig
import tech.ruslo.security.config.properties.SecurityProperties

/**
 * Тестирование поднятия spring контекста для стартера.
 *
 * @property securityProperties свойства для конфигурации стартера безопасности.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest(classes = [SecurityAutoConfiguration::class], webEnvironment = NONE)
@Import(TestConfig::class)
class SecurityAutoConfigurationTest {
    @Autowired
    private lateinit var securityProperties: SecurityProperties

    @Suppress("unused")
    @MockitoBean
    lateinit var userDetailsService: ReactiveUserDetailsService

    @Test
    fun `should load spring context`() {
        assertEquals(securityProperties.jwt.issuer.isNotBlank(), true)
        assertEquals(securityProperties.jwt.key.isNotBlank(), true)
        assertEquals(securityProperties.permitAllPaths.isNotEmpty(), true)
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
        /*@Bean
        fun reactiveUserDetailsService() = CustomUserDetailsService(userService)*/
    }
}