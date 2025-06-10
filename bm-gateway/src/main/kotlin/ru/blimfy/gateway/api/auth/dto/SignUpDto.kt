package ru.blimfy.gateway.api.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * DTO для регистрации нового пользователя.
 *
 * @property email электронная почта.
 * @property username имя пользователя.
 * @property password пароль.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Данные нового пользователя")
data class SignUpDto(
    @Email(message = "Email should be valid")
    //@Pattern(regexp = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    val email: String,

    @Size(min = 2, max = 32, message = "Usernames must be between 2 and 32 characters long")
    //@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]+")
    val username: String,

    @NotBlank(message = "Password is mandatory")
    val password: String,
)