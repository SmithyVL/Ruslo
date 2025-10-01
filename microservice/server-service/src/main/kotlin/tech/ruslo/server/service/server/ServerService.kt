package tech.ruslo.server.service.server

import kotlinx.coroutines.flow.Flow
import tech.ruslo.server.database.entity.Server

/**
 * Интерфейс для работы с серверами пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerService {
    /**
     * Возвращает сохранённый [server]. Если сохраняется новый сервер, то для него создаётся стандартная роль, участник
     * для владельца, который крепится к этой роли, и идентификатор роли записывается к серверу.
     */
    suspend fun saveServer(server: Server): Server

    /**
     * Возвращает сервера пользователя с идентификатором [userId].
     */
    fun findUserServers(userId: Long): Flow<Server>
}