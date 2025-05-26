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
     * Возвращает нового [user].
     */
    suspend fun createUser(user: User): User

    /**
     * Возвращает обновлённого пользователя с [id], [newGlobalName], [newAvatar] и [newBannerColor].
     */
    suspend fun modifyUser(
        id: UUID,
        newGlobalName: String? = null,
        newAvatar: String? = null,
        newBannerColor: String? = null,
    ): User

    /**
     * Возвращает обновлённого пользователя с [id] и новым [username].
     */
    suspend fun setUsername(id: UUID, username: String): User

    /**
     * Возвращает обновлённого пользователя с [id] и новым [password].
     */
    suspend fun setPassword(id: UUID, password: String): User

    /**
     * Возвращает обновлённого пользователя с [id] и новым [email].
     */
    suspend fun setEmail(id: UUID, email: String): User

    /**
     * Возвращает обновлённого пользователя с [id], который был верифицирован.
     */
    suspend fun setVerified(id: UUID): User

    /**
     * Возвращает пользователя с таким [id].
     */
    suspend fun findUser(id: UUID): User

    /**
     * Возвращает пользователя с таким [username], включая его хэш пароля.
     */
    suspend fun findUser(username: String): User

    /**
     * Возвращает удалённого пользователя с [id].
     */
    suspend fun deleteUser(id: UUID): User
}