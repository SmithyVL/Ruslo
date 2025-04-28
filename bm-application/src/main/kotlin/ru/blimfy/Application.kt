package ru.blimfy

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux


/**
 * Основной класс для запуска приложения.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootApplication
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@EnableWebFlux
class Application {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper =
        ObjectMapper().configure(WRITE_DATES_AS_TIMESTAMPS, false).registerModule(JavaTimeModule())

    companion object {
        /**
         * Формат дат для приложения.
         */
        const val INSTANT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
    }
}

/**
 * Запускает Spring Boot приложение с аргументами - [args].
 */
fun main(args: Array<String>) {
    run(Application::class.java, *args)
}