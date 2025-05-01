package ru.blimfy.server.db.repository

import java.util.UUID
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
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
     * Возвращает сущность дефолтной роли для сервера с идентификатором [serverId]
     */
    suspend fun findAllByServerIdAndBasicIsTrue(serverId: UUID): Role
}