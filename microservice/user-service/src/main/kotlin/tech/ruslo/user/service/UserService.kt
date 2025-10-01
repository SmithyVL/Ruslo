package tech.ruslo.user.service

import tech.ruslo.user.database.entity.User

/**
 * Интерфейс для работы с пользователем.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface UserService {
    /**
     * Возвращает сохранённого [user].
     */
    suspend fun saveUser(user: User): User

    /**
     * Возвращает пользователя, найденного по [id].
     */
    suspend fun findUser(id: Long): User

    /**
     * Возвращает пользователя, найденного по [username].
     */
    suspend fun findUser(username: String): User
}