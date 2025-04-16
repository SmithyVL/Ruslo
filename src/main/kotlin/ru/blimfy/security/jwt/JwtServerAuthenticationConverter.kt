package ru.blimfy.security.jwt

import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import ru.blimfy.security.service.TokenService.Companion.extractToken

/**
 * Конвертер запроса в объект аутентификации.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class JwtServerAuthenticationConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
        mono { unauthenticated(null, extractToken(exchange.request.headers)) }
}