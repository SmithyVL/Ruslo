package ru.blimfy.common.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

/**
 * DTO с токеном авторизации пользователя.
 *
 * @property token токен авторизации.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "JWT токен авторизации пользователя")
data class TokenDto(
    @field:Schema(description = "JWT токен", required = true)
    val token: String,
)