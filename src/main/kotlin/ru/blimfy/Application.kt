package ru.blimfy

import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
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
@ConfigurationPropertiesScan
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@EnableWebFlux
class Application

/**
 * Запускает Spring Boot приложение с аргументами - [args].
 */
fun main(args: Array<String>) {
    run(Application::class.java, *args)
}