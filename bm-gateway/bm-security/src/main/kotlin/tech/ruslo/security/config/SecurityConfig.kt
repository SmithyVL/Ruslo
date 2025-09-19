package tech.ruslo.security.config

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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import tech.ruslo.security.config.properties.SecurityProperties

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
     * Создаёт бин-конфигурацию CORS.
     */
    @Bean
    fun corsConfigurationSource() = UrlBasedCorsConfigurationSource().apply {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf(CORS_ALLOWED_ALL)
            allowedMethods = listOf(CORS_ALLOWED_ALL)
            allowedHeaders = listOf(CORS_ALLOWED_ALL)
        }

        registerCorsConfiguration(CORS_MAP_TO_ALL_PATH, configuration)
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
            addFilterAt(authFilter, AUTHENTICATION)

            authorizeExchange {
                val permitAllPaths = securityProperties.permitAllPaths
                if (permitAllPaths.isNotEmpty()) {
                    authorize(
                        pathMatchers(*permitAllPaths.toTypedArray()),
                        permitAll,
                    )
                }

                authorize()
            }

            // Отключаем лишнее от Spring Security.
            csrf { disable() }
            httpBasic { disable() }
            formLogin { disable() }
        }

    private companion object {
        /**
         * Указывает на поддержку всего для типа конфигураций CORS.
         */
        const val CORS_ALLOWED_ALL = "*"

        /**
         * Указывает на то, что настройки CORS маппятся на все пути приложения.
         */
        const val CORS_MAP_TO_ALL_PATH = "/**"
    }
}