package ru.blimfy.gateway.dto.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * DTO для авторизации пользователя.
 *
 * @property username имя пользователя.
 * @property password пароль.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Данные авторизации пользователя")
data class SignInDto(
    @field:Size(min = 2, max = 32, message = "Usernames must be between 2 and 32 characters long")
    val username: String,

    @field:NotBlank(message = "Password is mandatory")
    val password: String,
)