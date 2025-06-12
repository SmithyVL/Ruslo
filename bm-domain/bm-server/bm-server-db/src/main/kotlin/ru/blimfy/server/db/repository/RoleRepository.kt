package ru.blimfy.server.db.repository

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.server.db.entity.ROLE_DEFAULT_NAME
import ru.blimfy.server.db.entity.Role

/**
 * Репозиторий для работы с сущностью роли сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface RoleRepository : CoroutineCrudRepository<Role, UUID> {
    /**
     * Возвращает сущность дефолтной роли для сервера с [serverId].
     */
    @Query("select * from role where server_id = :serverId and name = $ROLE_DEFAULT_NAME")
    suspend fun findDefaultServerRole(serverId: UUID): Role

    /**
     * Возвращает сущности ролей сервера с [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Role>

    /**
     * Возвращает сущность роли с [id] для сервера с [serverId].
     */
    suspend fun findByIdAndServerId(id: UUID, serverId: UUID): Role?
}