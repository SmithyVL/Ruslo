package ru.blimfy.server.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.server.db.entity.Invite

/**
 * Репозиторий для работы с сущностью приглашения на сервер в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface InviteRepository : CoroutineCrudRepository<Invite, UUID> {
    /**
     * Возвращает все сущности приглашений на сервер с идентификатором [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Invite>

    /**
     * Удаляет сущность приглашения с идентификатором [inviteId] для сервера с идентификатором [serverId].
     */
    suspend fun deleteByIdAndServerId(inviteId: UUID, serverId: UUID)
}