package ru.blimfy.domain.server.db.repository

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.domain.server.db.entity.ROLE_DEFAULT_POSITION
import ru.blimfy.domain.server.db.entity.Role

/**
 * Репозиторий для работы с сущностью роли сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface RoleRepository : CoroutineCrudRepository<Role, UUID> {
    /**
     * Возвращает сущность первой роли для сервера с [serverId].
     */
    @Query("select * from role where server_id = :serverId and position = $ROLE_DEFAULT_POSITION")
    suspend fun findFirstServerRole(serverId: UUID): Role?

    /**
     * Возвращает сущности ролей сервера с [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Role>

    /**
     * Возвращает сущность роли с [id] для сервера с [serverId].
     */
    suspend fun findByIdAndServerId(id: UUID, serverId: UUID): Role?

    /**
     * Удаляет сущность роли с [id] для сервера с [serverId].
     */
    suspend fun deleteByIdAndServerId(id: UUID, serverId: UUID)
}