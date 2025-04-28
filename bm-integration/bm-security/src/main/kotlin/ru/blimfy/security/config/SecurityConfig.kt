package ru.blimfy.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers
import ru.blimfy.security.config.properties.SecurityProperties

/**
 * Конфигурация безопасности для бинов, отвечающих за безопасность.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Configuration
@EnableWebFluxSecurity
internal class SecurityConfig {
    /**
     * Создаёт бин для шифрования/дешифрования пароля.
     */
    @Bean
    fun encoder() = BCryptPasswordEncoder()

    /**
     * Создаёт бин-фильтр аутентификации. [authenticationConverter] извлекает из запроса данные авторизации.
     * [authenticationManager], непосредственно, проверяет авторизацию.
     */
    @Bean
    fun authenticationWebFilter(
        authenticationManager: ReactiveAuthenticationManager,
        authenticationConverter: ServerAuthenticationConverter,
    ) =
        AuthenticationWebFilter(authenticationManager).apply {
            setServerAuthenticationConverter(authenticationConverter)
        }

    /**
     * Создаёт бин, отвечающий за безопасность приложения. [http] - конфигурация безопасности приложения.
     * [authFilter] - настроенный фильтр аутентификации.
     */
    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        authFilter: AuthenticationWebFilter,
        securityProperties: SecurityProperties,
    ) =
        http {
            csrf { disable() }

            httpBasic { disable() }

            addFilterAt(authFilter, AUTHENTICATION)

            authorizeExchange {
                authorize(
                    pathMatchers(*securityProperties.permitAllPaths.toTypedArray()),
                    permitAll,
                )

                authorize()
            }
        }
}