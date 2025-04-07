package ru.blimfy.controllers.dto

/**
 * DTO для регистрации нового пользователя.
 *
 * @property email электронная почта.
 * @property username имя пользователя.
 * @property password пароль.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class SignUpDto(val email: String, val username: String, val password: String)