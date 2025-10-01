package tech.ruslo.user.dto

/**
 * Клиентский интерфейс для работы с информацией о пользователях из сервиса пользователей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface UserClient {
    /**
     * Возвращает сохранённого [userDto].
     */
    suspend fun saveUser(userDto: UserDto): UserDto

    /**
     * Возвращает пользователя, найденного по [id].
     */
    suspend fun getUser(id: Long): UserDto

    /**
     * Возвращает пользователя, найденного по [username].
     */
    suspend fun getUser(username: String): UserDto
}