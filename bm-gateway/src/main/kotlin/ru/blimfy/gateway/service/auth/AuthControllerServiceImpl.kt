package ru.blimfy.gateway.service.auth

import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.gateway.dto.auth.SignInDto
import ru.blimfy.gateway.dto.auth.SignUpDto
import ru.blimfy.gateway.dto.auth.TokenDto
import ru.blimfy.gateway.dto.auth.toUserEntity
import ru.blimfy.gateway.exception.GatewayErrors.INCORRECT_PASSWORD
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
): AuthControllerService {
    override suspend fun signUp(signUpDto: SignUpDto) =
        userService.createUser(signUpDto.toUserEntity(), encoder.encode(signUpDto.password))
            .let { createUserToken(it.username, it.id) }

    override suspend fun signIn(signInDto: SignInDto) =
        userService.findUser(signInDto.username)
            .apply { checkUserPassword(signInDto.password, passwordHash) }
            .let { createUserToken(it.username, it.id) }

    /**
     * Возвращает токен авторизации для пользователя с [username] и [userId].
     */
    private fun createUserToken(username: String, userId: UUID) =
        TokenDto(tokenService.generateToken(username, userId))

    /**
     * Проверяет совпадение [checkPassword] пользователя для авторизации с тем [userPassword], что установлен у
     * пользователя.
     */
    private fun checkUserPassword(checkPassword: String, userPassword: String) {
        if (!encoder.matches(checkPassword, userPassword)) {
            throw IncorrectDataException(INCORRECT_PASSWORD.msg.format(checkPassword))
        }
    }
}