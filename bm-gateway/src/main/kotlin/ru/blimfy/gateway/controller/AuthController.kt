package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.gateway.dto.auth.SignInDto
import ru.blimfy.gateway.dto.auth.SignUpDto
import ru.blimfy.gateway.dto.auth.TokenDto
import ru.blimfy.gateway.dto.auth.toUserEntity
import ru.blimfy.gateway.exception.GatewayErrors.INCORRECT_PASSWORD
import ru.blimfy.security.service.TokenService
import ru.blimfy.user.db.entity.Password
import ru.blimfy.user.usecase.password.PasswordService
import ru.blimfy.user.usecase.user.UserService

/**
 * Контроллер для работы с авторизацией и аутентификацией пользователя.
 *
 * @property userService сервис для работы с пользователями.
 * @property passwordService сервис для работы с паролями пользователей.
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "AuthController", description = "Контроллер для работы с авторизацией пользователей")
@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val userService: UserService,
    private val passwordService: PasswordService,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder,
) {
    @SecurityRequirements
    @Operation(summary = "Зарегистрировать нового пользователя")
    @PostMapping("/sign-up")
    suspend fun signUp(@RequestBody signUpDto: SignUpDto) =
        userService.saveUser(signUpDto.toUserEntity())
            .apply { passwordService.savePassword(Password(id, encoder.encode(signUpDto.password))) }
            .let { user -> TokenDto(tokenService.generateToken(user.username, user.id)) }

    @SecurityRequirements
    @Operation(summary = "Авторизовать существующего пользователя")
    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody signInDto: SignInDto) =
        userService.findUser(signInDto.username)
            .apply {
                val password = passwordService.findUserPassword(id)
                if (!encoder.matches(signInDto.password, password.hash)) {
                    throw IncorrectDataException(INCORRECT_PASSWORD.msg.format(signInDto.password, username))
                }
            }
            .let { user -> TokenDto(tokenService.generateToken(user.username, user.id)) }
}