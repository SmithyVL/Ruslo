package ru.blimfy.gateway.api.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.api.auth.handler.AuthApiService
import ru.blimfy.gateway.dto.auth.SignInDto
import ru.blimfy.gateway.dto.auth.SignUpDto

/**
 * REST API контроллер для работы с авторизацией/аутентификацией пользователей.
 *
 * @property service сервис для обработки информации авторизации пользователей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "AuthApi", description = "REST API контроллер для работы с авторизацией пользователей")
@RestController
@RequestMapping("/v1/auth")
class AuthApi(private val service: AuthApiService) {
    @SecurityRequirements
    @Operation(summary = "Зарегистрировать нового пользователя")
    @PostMapping("/sign-up")
    suspend fun signUp(@Valid @RequestBody signUpDto: SignUpDto) = service.signUp(signUpDto)

    @SecurityRequirements
    @Operation(summary = "Авторизовать существующего пользователя")
    @PostMapping("/sign-in")
    suspend fun signIn(@Valid @RequestBody signInDto: SignInDto) = service.signIn(signInDto)
}