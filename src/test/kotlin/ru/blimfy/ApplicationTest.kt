package ru.blimfy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.blimfy.security.config.JwtProperties

/**
 * Тестирование поднятия spring контекста для приложения.
 *
 * @property jwtProperties свойства для конфигурации JWT токенов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest
class ApplicationTest {
    @Autowired
    private lateinit var jwtProperties: JwtProperties

    @DisplayName("Тест поднятия контекста приложения")
    @Test
    fun contextLoadsTest() {
        assertEquals(jwtProperties.issuer.isNotBlank(), true)
        assertEquals(jwtProperties.key.isNotBlank(), true)
    }
}