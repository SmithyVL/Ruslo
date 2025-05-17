package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.auth.SignInDto
import ru.blimfy.gateway.dto.auth.SignUpDto
import ru.blimfy.gateway.service.auth.AuthControllerService

/**
 * Контроллер для обработки запросов авторизации/аутентификации пользователей.
 *
 * @property authControllerService сервис для обработки информации авторизации пользователей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "AuthController", description = "Контроллер для работы с авторизацией пользователей")
@RestController
@RequestMapping("/v1/auth/")
class AuthController(private val authControllerService: AuthControllerService) {
    @SecurityRequirements
    @Operation(summary = "Зарегистрировать нового пользователя")
    @PostMapping("sign-up")
    suspend fun signUp(@Valid @RequestBody signUpDto: SignUpDto) =
        authControllerService.signUp(signUpDto)

    @SecurityRequirements
    @Operation(summary = "Авторизовать существующего пользователя")
    @PostMapping("sign-in")
    suspend fun signIn(@Valid @RequestBody signInDto: SignInDto) =
        authControllerService.signIn(signInDto)
}