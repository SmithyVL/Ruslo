package tech.ruslo.gateway.controller.auth

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound
import tech.ruslo.gateway.controller.auth.dto.BooleanDto
import tech.ruslo.gateway.controller.auth.dto.SignInDto
import tech.ruslo.gateway.controller.auth.dto.SignUpDto
import tech.ruslo.gateway.controller.auth.dto.TokenDto
import tech.ruslo.gateway.controller.auth.dto.UsernameDto
import tech.ruslo.gateway.security.access.AccessService
import tech.ruslo.user.dto.UserClient
import tech.ruslo.user.dto.UserDto

/**
 * Обработчик для работы с авторизацией и аутентификацией пользователя.
 *
 * @property userClient клиент для обращений в сервис пользователей.
 * @property accessService сервис для работы с доступами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class AuthHandler(private val userClient: UserClient, private val accessService: AccessService) {
    /**
     * Возвращает токен авторизации для нового пользователя, созданного по информации из [signUpDto].
     */
    suspend fun signUp(signUpDto: SignUpDto): TokenDto {
        val password = accessService.encodePassword(signUpDto.password)
        val userDto = UserDto(username = signUpDto.username, passwordHash = password)
        return userClient.saveUser(userDto).let { createUserToken(it.username, it.id!!) }
    }

    /**
     * Возвращает токен авторизации для существующего пользователя по информации из [signInDto].
     */
    suspend fun signIn(signInDto: SignInDto) =
        userClient.getUser(signInDto.username)
            .apply { accessService.checkPassword(signInDto.password, passwordHash) }
            .let { createUserToken(it.username, it.id!!) }

    /**
     * Возвращает флаг того, что выбранное [usernameDto] является уникальным.
     */
    suspend fun isUniqueUsername(usernameDto: UsernameDto) = try {
        userClient.getUser(usernameDto.username)
        BooleanDto(false)
    } catch (_: NotFound) {
        BooleanDto(true)
    }

    /**
     * Возвращает DTO представление токена пользователя с [username] и [userId].
     */
    private fun createUserToken(username: String, userId: Long) =
        TokenDto(accessService.generateToken(username, userId))
}