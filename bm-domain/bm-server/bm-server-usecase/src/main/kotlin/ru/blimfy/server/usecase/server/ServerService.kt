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
     * Возвращает новый [server] пользователя.
     */
    suspend fun createServer(server: Server): Server

    /**
     * Возвращает обновлённый сервер с [id], [newName], [newIcon], [newBannerColor] и [newDescription].
     */
    suspend fun modifyServer(
        id: UUID,
        newName: String,
        newIcon: String? = null,
        newBannerColor: String? = null,
        newDescription: String? = null,
    ): Server

    /**
     * Возвращает обновлённый сервер с [id] и новым [ownerId].
     */
    suspend fun setOwner(id: UUID, ownerId: UUID): Server

    /**
     * Возвращает сервер с таким [id].
     */
    suspend fun findServer(id: UUID): Server

    /**
     * Удалить сервер с таким [serverId] его владельцем с идентификатором [ownerId].
     */
    suspend fun deleteServer(serverId: UUID, ownerId: UUID)

    /**
     * Возвращает нового пользователя с [userId] на сервер с [serverId].
     */
    suspend fun addNewMember(serverId: UUID, userId: UUID): Member
}