package ru.blimfy.security.jwt

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Component
import ru.blimfy.security.service.TokenService

/**
 * Менеджер аутентификации для проверки разрешений на выполнение дальнейших запросов.
 *
 * @property userDetailsService сервис для работы с данными пользователей.
 * @property tokenService сервис для работы с токенами авторизации.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
internal class JwtAuthenticationManager(
    private val userDetailsService: ReactiveUserDetailsService,
    private val tokenService: TokenService,
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication) = mono {
        val token = (authentication.credentials as? String)
        if (token == null) {
            authentication
        } else {
            val username = tokenService.extractUsername(token)
            val user = userDetailsService.findByUsername(username).awaitSingleOrNull()
            user
                ?.let {
                    if (!tokenService.isValid(token, user.username)) {
                        authentication
                    } else {
                        authenticated(user, token, null)
                    }
                }
                ?: authentication
        }
    }
}