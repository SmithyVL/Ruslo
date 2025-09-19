package tech.ruslo.domain.server.usecase.ban

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import tech.ruslo.domain.server.db.entity.Ban

/**
 * Интерфейс для работы с банами сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface BanService {
    /**
     * Создаёт [ban] сервера.
     */
    suspend fun createBan(ban: Ban): Ban

    /**
     * Возвращает бан сервера с [serverId] для пользователя с [userId].
     */
    suspend fun findBan(serverId: UUID, userId: UUID): Ban?

    /**
     * Возвращает страницу банов для сервера с [serverId] с [before] или [after] или последние [limit] записей.
     */
    suspend fun findBans(serverId: UUID, limit: Int, before: UUID? = null, after: UUID? = null): Flow<Ban>

    /**
     * Возвращает баны сервера с [serverId].
     */
    fun findBans(serverId: UUID): Flow<Ban>

    /**
     * Удаляет бан сервера с [serverId] для пользователя с [userId].
     */
    suspend fun removeBan(serverId: UUID, userId: UUID): Ban
}