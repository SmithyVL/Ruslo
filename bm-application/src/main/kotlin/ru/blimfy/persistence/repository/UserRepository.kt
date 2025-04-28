package ru.blimfy.persistence.repository

import java.util.UUID
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.User

/**
 * Репозиторий для работы с сущностью пользователя в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface UserRepository : CoroutineCrudRepository<User, UUID> {
    /**
     * Возвращает сущность пользователя с таким [username].
     */
    suspend fun findByUsername(username: String): User?
}