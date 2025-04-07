package ru.blimfy.security

import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import ru.blimfy.security.TokenService.Companion.HEADER_AUTH_TOKEN_PREFIX

/**
 * Конвертер запроса в объект аутентификации.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class JwtServerAuthenticationConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
        mono {
            val authToken = exchange.request.headers
                .getFirst(AUTHORIZATION)
                ?.substringAfter(HEADER_AUTH_TOKEN_PREFIX)
            unauthenticated(null, authToken)
        }
}