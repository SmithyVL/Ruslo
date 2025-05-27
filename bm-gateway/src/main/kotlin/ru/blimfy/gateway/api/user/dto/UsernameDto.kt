package ru.blimfy.gateway.api.user.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * DTO с информацией для обновления имени пользователя.
 *
 * @property username новое имя пользователя.
 * @property password пароль пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class UsernameDto(
    @Size(min = 2, max = 32, message = "Usernames must be between 2 and 32 characters long")
    val username: String,

    @NotBlank(message = "Password is mandatory")
    val password: String,
)