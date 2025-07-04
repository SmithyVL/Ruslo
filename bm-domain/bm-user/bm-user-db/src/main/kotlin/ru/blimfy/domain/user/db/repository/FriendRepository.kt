package ru.blimfy.domain.user.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.domain.user.db.entity.Friend

/**
 * Репозиторий для работы с сущностью друзей в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface FriendRepository : CoroutineCrudRepository<Friend, UUID> {
    /**
     * Возвращает сущности друзей у пользователя с [fromId].
     */
    fun findAllByFromId(fromId: UUID): Flow<Friend>

    /**
     * Возвращает сущность друга для пользователя с [fromId] с пользователем с [toId].
     */
    suspend fun findByFromIdAndToId(fromId: UUID, toId: UUID): Friend?

    /**
     * Удаляет сущность друга между пользователем с [fromId] и пользователем с [toId].
     */
    suspend fun deleteByFromIdAndToId(fromId: UUID, toId: UUID)
}