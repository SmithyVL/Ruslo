package tech.ruslo.gateway.controller.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.gateway.controller.auth.dto.SignInDto
import tech.ruslo.gateway.controller.auth.dto.SignUpDto
import tech.ruslo.gateway.controller.auth.dto.UsernameDto

/**
 * REST API контроллер для работы с авторизацией/аутентификацией пользователей.
 *
 * @property authHandler обработчик информации авторизации пользователей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "AuthController", description = "REST API контроллер для работы с авторизацией пользователей")
@RestController
@RequestMapping("/auth")
class AuthController(private val authHandler: AuthHandler) {
    @SecurityRequirements
    @Operation(summary = "Зарегистрировать нового пользователя")
    @PostMapping("/sign-up")
    suspend fun signUp(@Valid @RequestBody signUpDto: SignUpDto) = authHandler.signUp(signUpDto)

    @SecurityRequirements
    @Operation(summary = "Авторизовать существующего пользователя")
    @PostMapping("/sign-in")
    suspend fun signIn(@Valid @RequestBody signInDto: SignInDto) = authHandler.signIn(signInDto)

    @SecurityRequirements
    @Operation(summary = "Проверить уникальность вводимого имени нового пользователя")
    @PostMapping("/unique-username")
    suspend fun isUniqueUsername(@Valid @RequestBody usernameDto: UsernameDto) =
        authHandler.isUniqueUsername(usernameDto)
}