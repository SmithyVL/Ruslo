package ru.blimfy.gateway.dto.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import ru.blimfy.user.db.entity.User

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
    val email: String,

    @Size(min = 2, max = 32, message = "Usernames must be between 2 and 32 characters long")
    val username: String,

    @NotBlank(message = "Password is mandatory")
    val password: String,
)

/**
 * Возвращает сущность пользователя из DTO регистрации нового пользователя.
 */
fun SignUpDto.toUserEntity(password: String) = User(username, email, password)