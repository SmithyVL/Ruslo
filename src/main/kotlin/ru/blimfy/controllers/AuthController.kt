package ru.blimfy.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.PasswordDto
import ru.blimfy.controllers.dto.SignInDto
import ru.blimfy.controllers.dto.SignUpDto
import ru.blimfy.controllers.dto.TokenDto
import ru.blimfy.exception.Errors.INCORRECT_PASSWORD
import ru.blimfy.exception.IncorrectPasswordException
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toUserEntity
import ru.blimfy.security.TokenService
import ru.blimfy.services.password.PasswordService
import ru.blimfy.services.user.UserService

/**
 * REST API контроллер для работы с авторизацией и аутентификацией пользователя.
 *
 * @property userService сервис для работы с пользователями.
 * @property passwordService сервис для работы с паролями пользователей.
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "AuthController", description = "REST API для работы с авторизацией/аутентификацией пользователей")
@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val userService: UserService,
    private val passwordService: PasswordService,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder,
) {
    @Transactional
    @SecurityRequirements
    @Operation(summary = "Зарегистрировать нового пользователя")
    @PostMapping("/sign-up")
    suspend fun registerUser(@RequestBody signUpDto: SignUpDto) =
        userService.saveUser(signUpDto.toUserEntity()).let { user ->
            val userId = user.id
            val password = PasswordDto(userId, encoder.encode(signUpDto.password))
            passwordService.savePassword(password)
            TokenDto(tokenService.generateToken(user.username, userId))
        }

    @SecurityRequirements
    @Operation(summary = "Авторизовать существующего пользователя")
    @PostMapping("/sign-in")
    suspend fun login(@RequestBody signInDto: SignInDto) =
        userService.findUser(signInDto.username).let { user ->
            passwordService.findUserPassword(user.id).let { password ->
                val username = user.username
                if (!encoder.matches(signInDto.password, password.hash)) {
                    throw IncorrectPasswordException(
                        INCORRECT_PASSWORD.msg.format(signInDto.password, username)
                    )
                }

                TokenDto(tokenService.generateToken(username, user.id))
            }
        }

    @Operation(summary = "Получить информацию о пользователе из JWT")
    @GetMapping
    suspend fun getCurrentUser(principal: Principal) =
        userService.findUser(tokenService.extractUserId(principal)).toDto()
}