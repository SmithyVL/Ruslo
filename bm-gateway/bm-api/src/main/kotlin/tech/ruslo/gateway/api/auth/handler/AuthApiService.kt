package tech.ruslo.gateway.api.auth.handler

import tech.ruslo.gateway.dto.auth.SignInDto
import tech.ruslo.gateway.dto.auth.SignUpDto
import tech.ruslo.gateway.dto.auth.TokenDto

/**
 * Интерфейс для работы с авторизацией и аутентификацией пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface AuthApiService {
    /**
     * Возвращает токен авторизации для пользователя, созданного из [signUpDto].
     */
    suspend fun signUp(signUpDto: SignUpDto): TokenDto

    /**
     * Возвращает токен авторизации для существующего пользователя, который прошёл авторизацию по информации из
     * [signInDto].
     */
    suspend fun signIn(signInDto: SignInDto): TokenDto
}