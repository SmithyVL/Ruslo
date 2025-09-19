package tech.ruslo.domain.user.db.repository

import java.util.UUID
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.domain.user.db.entity.User

/**
 * Репозиторий для работы с сущностью пользователя в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface UserRepository : CoroutineCrudRepository<User, UUID> {
    /**
     * Возвращает сущность пользователя с [username].
     */
    suspend fun findByUsername(username: String): User?
}