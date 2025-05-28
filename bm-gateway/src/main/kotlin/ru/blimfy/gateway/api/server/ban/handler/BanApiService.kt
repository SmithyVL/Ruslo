package ru.blimfy.gateway.api.server.ban.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.api.server.dto.ban.BanDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о банах серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface BanApiService {
    /**
     * Возвращает страницу с [limit] банов сервера с [serverId], которые хочет получить [user]. Для поиска позиции
     * используются параметры [before] и [after].
     */
    suspend fun findBans(
        serverId: UUID,
        before: UUID? = null,
        after: UUID? = null,
        limit: Int,
        user: User,
    ): Flow<BanDto>

    /**
     * Возвращает страницу с [limit] банов сервера с [serverId], которые хочет получить [user]. Для поиска используется
     * [query] с ником пользователя.
     */
    suspend fun searchBans(serverId: UUID, query: String, limit: Int, user: User): Flow<BanDto>

    /**
     * Создаёт бан с [reason] для сервера с [serverId] и для пользователя с [userId], которого хочет заблокировать
     * [user].
     */
    suspend fun createBan(serverId: UUID, userId: UUID, reason: String? = null, user: User)

    /**
     * Удаляет бан для сервера с [serverId] и для пользователя с [userId], которого хочет разблокировать [user].
     */
    suspend fun removeBan(serverId: UUID, userId: UUID, user: User)
}