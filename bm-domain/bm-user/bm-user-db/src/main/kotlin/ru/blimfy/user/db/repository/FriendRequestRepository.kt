package ru.blimfy.user.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.user.db.entity.FriendRequest

/**
 * Репозиторий для работы с сущностью запросов в друзья в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface FriendRequestRepository : CoroutineCrudRepository<FriendRequest, UUID> {
    /**
     * Возвращает сущности запросов в друзья для пользователя с [fromId].
     */
    fun findAllByFromId(fromId: UUID): Flow<FriendRequest>

    /**
     * Возвращает сущности запросов в друзья для пользователя с [toId].
     */
    fun findAllByToId(toId: UUID): Flow<FriendRequest>

    /**
     * Удаляет сущность запроса в друзья с [id] для пользователя с [fromId].
     */
    suspend fun deleteByIdAndFromId(id: UUID, fromId: UUID)

    /**
     * Удаляет сущность запроса в друзья с [id] для пользователя с [toId].
     */
    suspend fun deleteByIdAndToId(id: UUID, toId: UUID)

    /**
     * Удаляет сущности запросов в друзья для пользователя с [fromId].
     */
    suspend fun deleteAllByFromId(fromId: UUID)

    /**
     * Удаляет сущности запросов в друзья для пользователя с [toId].
     */
    suspend fun deleteAllByToId(toId: UUID)
}