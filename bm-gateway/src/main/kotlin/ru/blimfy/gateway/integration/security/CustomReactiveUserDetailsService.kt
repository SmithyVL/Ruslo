package ru.blimfy.gateway.integration.security

import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.user.usecase.user.UserService

/**
 * Сервис для поиска информации об авторизующихся пользователях.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class CustomReactiveUserDetailsService(private val userService: UserService) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> = mono {
        try {
            CustomUserDetails(userService.findUser(username))
        } catch (_: NotFoundException) {
            null
        }
    }
}