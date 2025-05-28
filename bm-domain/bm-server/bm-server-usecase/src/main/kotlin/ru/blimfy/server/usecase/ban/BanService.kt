package ru.blimfy.server.usecase.ban

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.server.db.entity.Ban

/**
 * Интерфейс для работы с банами сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface BanService {
    /**
     * Возвращает бан с [id].
     */
    suspend fun findBan(id: UUID): Ban

    /**
     * Возвращает бан сервера с [serverId] для пользователя с [userId].
     */
    suspend fun findBan(serverId: UUID, userId: UUID): Ban?

    /**
     * Возвращает страницу банов для сервера с [serverId] с позиции [start] по [end].
     */
    fun findBans(serverId: UUID, start: Long, end: Long): Flow<Ban>

    /**
     * Возвращает баны сервера с [serverId].
     */
    fun findBans(serverId: UUID): Flow<Ban>

    /**
     * Создаёт [ban] сервера.
     */
    suspend fun createBan(ban: Ban): Ban

    /**
     * Удаляет бан сервера с [serverId] для пользователя с [userId].
     */
    suspend fun removeBan(serverId: UUID, userId: UUID): Ban

    /**
     * Возвращает количество банов сервера с [serverId].
     */
    suspend fun getCountBans(serverId: UUID): Long
}