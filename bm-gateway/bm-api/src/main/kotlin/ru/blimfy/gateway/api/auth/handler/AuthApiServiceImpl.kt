package ru.blimfy.gateway.api.auth.handler

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.access.control.service.AccessService
import ru.blimfy.gateway.dto.auth.SignInDto
import ru.blimfy.gateway.dto.auth.SignUpDto
import ru.blimfy.gateway.dto.auth.TokenDto

/**
 * Реализация интерфейса для работы с авторизацией и аутентификацией пользователя.
 *
 * @property userService сервис для работы с пользователями.
 * @property accessService сервис для работы с доступами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class AuthApiServiceImpl(
    private val userService: UserService,
    private val accessService: AccessService,
) : AuthApiService {
    override suspend fun signUp(signUpDto: SignUpDto): TokenDto {
        val password = accessService.encodePassword(signUpDto.password)
        val user = User(signUpDto.username, signUpDto.email, password)
        return userService.createUser(user).let { createUserToken(it.username, it.id) }
    }

    override suspend fun signIn(signInDto: SignInDto) =
        userService.findUser(signInDto.username)
            .apply { accessService.checkPassword(signInDto.password, password) }
            .let { createUserToken(it.username, it.id) }

    /**
     * Возвращает DTO представление токена пользователя с [username] и [userId].
     */
    private fun createUserToken(username: String, userId: UUID) =
        TokenDto(accessService.generateToken(username, userId))
}