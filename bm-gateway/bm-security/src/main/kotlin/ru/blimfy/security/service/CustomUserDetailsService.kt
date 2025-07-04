package ru.blimfy.security.service

import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.domain.user.usecase.user.UserService

/**
 * Сервис для поиска информации об авторизующихся пользователях.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class CustomUserDetailsService(private val userService: UserService) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> = mono {
        try {
            CustomUserDetails(userService.findUser(username))
        } catch (_: NotFoundException) {
            null
        }
    }
}

/**
 * Информация об авторизованном пользователе.
 *
 * @property info информация пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class CustomUserDetails(val info: User) : UserDetails {
    override fun getAuthorities() = emptyList<GrantedAuthority>()
    override fun getPassword() = info.password
    override fun getUsername() = info.username
}