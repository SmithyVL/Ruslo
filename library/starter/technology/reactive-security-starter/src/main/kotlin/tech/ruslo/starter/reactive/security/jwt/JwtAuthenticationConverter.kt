package tech.ruslo.starter.reactive.security.jwt

import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * Конвертер запроса в объект аутентификации.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
internal class JwtAuthenticationConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
        mono { unauthenticated(null, extractToken(exchange.request.headers)) }

    /**
     * Возвращает JWT токен из [headers] или null, если его нет.
     */
    private fun extractToken(headers: HttpHeaders) =
        headers.getFirst("Authorization")?.substringAfter("Bearer ")
}