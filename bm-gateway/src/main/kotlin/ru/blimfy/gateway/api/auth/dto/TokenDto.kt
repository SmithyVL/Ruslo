package ru.blimfy.gateway.api.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

/**
 * DTO с токеном авторизации пользователя.
 *
 * @property token токен авторизации.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "JWT токен авторизации пользователя")
data class TokenDto(val token: String)