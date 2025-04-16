package ru.blimfy.security.service

import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User.builder
import org.springframework.stereotype.Service
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.User
import ru.blimfy.services.password.PasswordService
import ru.blimfy.services.user.UserService

/**
 * Сервис для поиска информации об авторизующихся пользователях.
 *
 * @property userService сервис для работы с пользователями.
 * @property passwordService сервис для работы с паролями пользователей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class CustomReactiveUserDetailsService(
    private val userService: UserService,
    private val passwordService: PasswordService,
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String) = mono {
        try {
            userService.findUser(username).mapToUserDetails()
        } catch (e: NotFoundException) {
            null
        }
    }

    /**
     * Возвращает детальную информацию о пользователе в разрезе безопасности приложения.
     */
    private suspend fun User.mapToUserDetails() =
        builder()
            .username(this.username)
            .password(passwordService.findUserPassword(this.id).hash)
            .build()
}