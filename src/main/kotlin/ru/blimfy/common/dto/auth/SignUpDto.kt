package ru.blimfy.common.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

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
    @field:Schema(description = "Электронная почта", required = true)
    val email: String,

    @field:Schema(description = "Имя пользователя", required = true)
    val username: String,

    @field:Schema(description = "Пароль", required = true)
    val password: String,
)