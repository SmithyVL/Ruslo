package tech.ruslo.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

/**
 * Основной класс для запуска приложения для работы со шлюзом API.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@EnableWebFlux
@SpringBootApplication
class GatewayApplication

/**
 * Запускает Spring Boot приложение с [args].
 */
fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}