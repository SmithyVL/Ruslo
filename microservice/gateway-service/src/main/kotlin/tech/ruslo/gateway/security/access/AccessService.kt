package tech.ruslo.gateway.security.access

/**
 * Интерфейс для работы с разрешениями, токенами и шифрованием/дешифрованием паролей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface AccessService {
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
    fun generateToken(username: String, userId: Long): String
}