package ru.blimfy.common.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

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
    @field:Schema(description = "Имя пользователя", required = true)
    val username: String,

    @field:Schema(description = "Пароль", required = true)
    val password: String,
)