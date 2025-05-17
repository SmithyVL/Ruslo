package ru.blimfy.user.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.user.db.entity.Friend

/**
 * Репозиторий для работы с сущностью друзей в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface FriendRepository : CoroutineCrudRepository<Friend, UUID> {
    /**
     * Возвращает сущность друга с [toId] у пользователя с [fromId].
     */
    suspend fun findByFromIdAndToId(fromId: UUID, toId: UUID): Friend?

    /**
     * Возвращает сущности друзей у пользователя с [fromId].
     */
    fun findAllByFromId(fromId: UUID): Flow<Friend>

    /**
     * Удаляет сущность друга с [id] у пользователя с [fromId].
     */
    suspend fun deleteByIdAndFromId(id: UUID, fromId: UUID)
}