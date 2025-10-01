package tech.ruslo.gateway.security

import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tech.ruslo.user.dto.UserClient

/**
 * Сервис для поиска информации об авторизующихся пользователях.
 *
 * @property userClient клиент для обращений в сервис пользователей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class CustomUserDetailsService(private val userClient: UserClient) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> = mono {
        try {
            CustomUserDetails(userClient.getUser(username))
        } catch (_: Exception) {
            null
        }
    }
}