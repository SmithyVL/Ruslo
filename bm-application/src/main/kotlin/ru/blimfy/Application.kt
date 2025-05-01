package ru.blimfy

import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.web.reactive.config.EnableWebFlux

/**
 * Основной класс для запуска приложения.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@SpringBootApplication
@EnableWebFlux
@EnableR2dbcAuditing
class Application

/**
 * Запускает Spring Boot приложение с аргументами - [args].
 */
fun main(args: Array<String>) {
    run(Application::class.java, *args)
}