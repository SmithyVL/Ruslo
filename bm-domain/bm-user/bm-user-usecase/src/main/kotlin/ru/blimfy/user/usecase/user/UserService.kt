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
     * Возвращает нового или обновлённого [user].
     */
    suspend fun saveUser(user: User): User

    /**
     * Возвращает пользователя с таким [id].
     */
    suspend fun findUser(id: UUID): User

    /**
     * Возвращает пользователя с таким [username].
     */
    suspend fun findUser(username: String): User
}