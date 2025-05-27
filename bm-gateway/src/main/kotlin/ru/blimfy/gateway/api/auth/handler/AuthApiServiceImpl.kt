package ru.blimfy.gateway.api.auth.handler

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.gateway.api.auth.dto.SignInDto
import ru.blimfy.gateway.api.auth.dto.SignUpDto
import ru.blimfy.gateway.api.auth.dto.TokenDto
import ru.blimfy.gateway.api.auth.dto.toUserEntity
import ru.blimfy.gateway.service.AccessService
import ru.blimfy.user.usecase.user.UserService

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
    override suspend fun signUp(signUpDto: SignUpDto) =
        userService.createUser(signUpDto.toUserEntity(accessService.encodePassword(signUpDto.password)))
            .let { createUserToken(it.username, it.id) }

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