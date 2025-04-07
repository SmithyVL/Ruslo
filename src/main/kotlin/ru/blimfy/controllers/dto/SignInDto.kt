package ru.blimfy.controllers.dto

/**
 * DTO для авторизации пользователя.
 *
 * @property username имя пользователя.
 * @property password пароль.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class SignInDto(val username: String, val password: String)