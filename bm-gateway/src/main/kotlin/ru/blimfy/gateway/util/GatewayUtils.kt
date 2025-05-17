package ru.blimfy.gateway.util

import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.gateway.dto.auth.TokenDto
import ru.blimfy.gateway.exception.GatewayErrors.INCORRECT_PASSWORD
import ru.blimfy.security.service.TokenService

/**
 * Класс с утилитарными методами шлюза обработки API.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
object GatewayUtils {
    /**
     * Проверяет совпадение [checkPassword] с тем [userPassword], что установлен у пользователя с помощью [encoder].
     */
    fun checkUserPassword(encoder: PasswordEncoder, checkPassword: String, userPassword: String) {
        if (!encoder.matches(checkPassword, userPassword)) {
            throw IncorrectDataException(INCORRECT_PASSWORD.msg.format(checkPassword))
        }
    }

    /**
     * Возвращает токен авторизации для пользователя с [username] и [userId] с помощью [tokenService].
     */
    fun createUserToken(tokenService: TokenService, username: String, userId: UUID) =
        TokenDto(tokenService.generateToken(username, userId))
}