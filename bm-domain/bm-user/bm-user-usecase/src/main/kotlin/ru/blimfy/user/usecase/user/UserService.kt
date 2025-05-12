package ru.blimfy.user.usecase.user

import java.util.UUID
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с пользователем.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface UserService {
    /**
     * Возвращает нового [user], для которого был так же установлен [passwordHash].
     */
    suspend fun createUser(user: User, passwordHash: String): User

    /**
     * Возвращает обновлённого [user].
     */
    suspend fun modifyUser(user: User): User

    /**
     * Возвращает пользователя с таким [id].
     */
    suspend fun findUser(id: UUID): User

    /**
     * Возвращает пользователя с таким [username], включая его хэш пароля.
     */
    suspend fun findUser(username: String): User
}