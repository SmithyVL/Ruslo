package tech.ruslo.channel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.web.reactive.config.EnableWebFlux

/**
 * Основной класс для запуска приложения для работы с каналами.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@EnableWebFlux
@EnableR2dbcAuditing
@SpringBootApplication
class ChannelApplication

/**
 * Запускает Spring Boot приложение с [args].
 */
fun main(args: Array<String>) {
    runApplication<ChannelApplication>(*args)
}