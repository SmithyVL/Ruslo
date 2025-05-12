package ru.blimfy.gateway.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * Конфигурация Web.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Configuration
class WebConfig {
    /**
     * Возвращает бин конфигурации Swagger.
     */
    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .addSecurityItem(SecurityRequirement().addList(SECURITY_BEARER_KEY))
        .components(
            Components()
                .addSecuritySchemes(
                    SECURITY_BEARER_KEY,
                    SecurityScheme().type(HTTP).scheme(SCHEME_BEARER).bearerFormat(BEARER_FORMAT),
                ),
        )
        .info(Info().title(SWAGGER_TITLE).description(SWAGGER_DESC).version(SWAGGER_VERSION))

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper =
        ObjectMapper().configure(WRITE_DATES_AS_TIMESTAMPS, false).registerModule(JavaTimeModule())

    companion object {
        /**
         * Формат дат для приложения.
         */
        const val INSTANT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX"

        /**
         * Формат дат для приложения.
         */
        const val INSTANT_TIMEZONE = "UTC"

        /**
         * Название типа авторизации через "bearer" токен.
         */
        private const val SECURITY_BEARER_KEY = "JWT авторизация"

        /**
         * Название схемы авторизации через "bearer".
         */
        private const val SCHEME_BEARER = "bearer"

        /**
         * Формат "bearer" авторизации.
         */
        private const val BEARER_FORMAT = "JWT"

        /**
         * Название документации Swagger.
         */
        private const val SWAGGER_TITLE = "Blimfy Swagger"

        /**
         * Описание документации Swagger.
         */
        private const val SWAGGER_DESC = "Официальная документация API"

        /**
         * Номер версии API.
         */
        private const val SWAGGER_VERSION = "v1"
    }
}