package ru.blimfy.gateway.service.auth

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.auth.SignInDto
import ru.blimfy.gateway.dto.auth.SignUpDto
import ru.blimfy.gateway.dto.auth.toUserEntity
import ru.blimfy.gateway.util.GatewayUtils.checkUserPassword
import ru.blimfy.gateway.util.GatewayUtils.createUserToken
import ru.blimfy.security.service.TokenService
import ru.blimfy.user.usecase.user.UserService

/**
 * Реализация интерфейса для работы с авторизацией и аутентификацией пользователя.
 *
 * @property userService сервис для работы с пользователями.
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class AuthControllerServiceImpl(
    private val userService: UserService,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder,
) : AuthControllerService {
    override suspend fun signUp(signUpDto: SignUpDto) =
        userService.createUser(signUpDto.toUserEntity(encoder.encode(signUpDto.password)))
            .let { createUserToken(tokenService, it.username, it.id) }

    override suspend fun signIn(signInDto: SignInDto) =
        userService.findUser(signInDto.username)
            .apply { checkUserPassword(encoder, signInDto.password, password) }
            .let { createUserToken(tokenService, it.username, it.id) }
}