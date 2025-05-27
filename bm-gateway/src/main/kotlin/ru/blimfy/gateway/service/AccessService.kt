package ru.blimfy.gateway.service

import java.util.UUID

/**
 * Интерфейс для работы с разрешениями, токенами и шифрованием/дешифрованием паролей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface AccessService {
    /**
     * Возвращает флаг того, что пользователь с [userId] является владельцем сервера с [serverId]
     */
    suspend fun isServerOwner(serverId: UUID, userId: UUID): Boolean

    /**
     * Проверяет совпадение [checkPassword] с текущим [password].
     */
    fun checkPassword(checkPassword: String, password: String)

    /**
     * Возвращает хэш для [password].
     */
    fun encodePassword(password: String): String

    /**
     * Возвращает токен авторизации для пользователя с [username] и [userId].
     */
    fun generateToken(username: String, userId: UUID): String
}