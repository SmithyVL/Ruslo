package ru.blimfy.server.usecase.server

import java.util.UUID
import ru.blimfy.server.db.entity.Member
import ru.blimfy.server.db.entity.Server

/**
 * Интерфейс для работы с серверами пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerService {
    /**
     * Возвращает новый [server] пользователя с [ownerUsername].
     */
    suspend fun createServer(server: Server, ownerUsername: String): Server

    /**
     * Возвращает обновлённый [server].
     */
    suspend fun modifyServer(server: Server): Server

    /**
     * Возвращает сервер с таким [id].
     */
    suspend fun findServer(id: UUID): Server

    /**
     * Удалить сервер с таким [id] его владельцем с идентификатором [ownerId].
     */
    suspend fun deleteServer(id: UUID, ownerId: UUID)

    /**
     * Возвращает нового пользователя с [userId] и [username] на сервер с [serverId].
     */
    suspend fun addNewMember(serverId: UUID, userId: UUID, username: String): Member

    /**
     * Проверяет разрешение пользователя с [userId] на изменение сервера с [serverId] и его связанных данных.
     */
    suspend fun checkServerModifyAccess(serverId: UUID, userId: UUID)

    /**
     * Проверяет разрешение пользователя с [userId] на просмотр сервера с [serverId] и его связанных данных.
     */
    suspend fun checkServerViewAccess(serverId: UUID, userId: UUID)
}