package ru.blimfy.server.db.repository

import java.util.UUID
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.server.db.entity.Server

/**
 * Репозиторий для работы с сущностью сервера пользователя в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface ServerRepository : CoroutineCrudRepository<Server, UUID> {
    /**
     * Удаляет сущность сервера с идентификатором [serverId], владельцем которого является пользователь с идентификатором
     * [ownerId].
     */
    suspend fun deleteByIdAndOwnerUserId(serverId: UUID, ownerId: UUID)
}