package ru.blimfy.services.password

import java.util.UUID
import ru.blimfy.persistence.entity.Password

/**
 * Интерфейс для работы с паролем пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface PasswordService {
    /**
     * Возвращает новый или обновлённый [password].
     */
    suspend fun savePassword(password: Password): Password

    /**
     * Возвращает пароль пользователя с идентификатором [userId].
     */
    suspend fun findUserPassword(userId: UUID): Password
}