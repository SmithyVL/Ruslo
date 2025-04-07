package ru.blimfy.persistence.repository

import java.util.UUID
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.Password

/**
 * Репозиторий для работы с сущностью пароля пользователя в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface PasswordRepository : CoroutineCrudRepository<Password, UUID> {
    /**
     * Возвращает сущность пароля для пользователя с идентификатором [userId].
     */
    suspend fun findByUserId(userId: UUID): Password?
}