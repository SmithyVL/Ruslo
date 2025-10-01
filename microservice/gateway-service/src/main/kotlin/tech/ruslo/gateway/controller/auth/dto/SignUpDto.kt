package tech.ruslo.gateway.controller.auth.dto

/**
 * DTO для регистрации нового пользователя.
 *
 * @property username имя пользователя.
 * @property password пароль.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class SignUpDto(val username: String, val password: String)