package tech.ruslo.user.database.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.user.database.entity.User

/**
 * Репозиторий для работы с сущностью пользователя в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface UserRepository : CoroutineCrudRepository<User, Long> {
    /**
     * Возвращает сущность пользователя с [username].
     */
    suspend fun findByUsername(username: String): User?
}