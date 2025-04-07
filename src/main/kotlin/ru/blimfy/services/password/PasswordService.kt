package ru.blimfy.services.password

import java.util.UUID
import ru.blimfy.common.dto.PasswordDto

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
    suspend fun savePassword(password: PasswordDto): PasswordDto

    /**
     * Возвращает пароль пользователя с идентификатором [userId].
     */
    suspend fun findUserPassword(userId: UUID): PasswordDto
}