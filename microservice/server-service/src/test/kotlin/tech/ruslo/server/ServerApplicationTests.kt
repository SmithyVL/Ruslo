package tech.ruslo.server

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.postgresql.PostgreSQLContainer

/**
 * Тестирование поднятия spring контекста для приложения.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootTest
class ServerApplicationTests {
    @Test
    fun `context loads`() {
        // Реализация не требуется.
    }

    private companion object {
        /**
         * Название Docker образа для БД Postgres.
         */
        const val POSTGRES_IMAGE_NAME = "postgres:latest"

        /**
         * Тестовый контейнер для БД Postgres.
         */
        var postgres = PostgreSQLContainer(POSTGRES_IMAGE_NAME)

        @BeforeAll
        @JvmStatic
        fun setup() = postgres.start()

        @AfterAll
        @JvmStatic
        fun tearDown() = postgres.stop()

        /**
         * Конфигурирует свойства spring в [registry] для работы с тестовым контейнером, а не реальной БД.
         */
        @DynamicPropertySource
        @JvmStatic
        @Suppress("unused")
        fun configureProperties(registry: DynamicPropertyRegistry) {
            val postgresUrl = ":postgresql://${postgres.host}:${postgres.firstMappedPort}/${postgres.databaseName}"
            registry.add("spring.r2dbc.url") { "r2dbc$postgresUrl" }
            registry.add("spring.liquibase.url") { "jdbc$postgresUrl" }

            val pgUsername = postgres.username
            registry.add("spring.r2dbc.username") { pgUsername }
            registry.add("spring.liquibase.user") { pgUsername }

            val pgPassword = postgres.password
            registry.add("spring.r2dbc.password") { pgPassword }
            registry.add("spring.liquibase.password") { pgPassword }
        }
    }
}