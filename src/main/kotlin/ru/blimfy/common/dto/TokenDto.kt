package ru.blimfy.common.dto

/**
 * DTO с токеном авторизации пользователя.
 *
 * @property token токен авторизации.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class TokenDto(val token: String)