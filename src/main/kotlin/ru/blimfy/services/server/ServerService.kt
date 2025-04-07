package ru.blimfy.services.server

import java.util.UUID
import ru.blimfy.persistence.entity.Server

/**
 * Интерфейс для работы с серверами пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerService {
    /**
     * Возвращает новый или обновлённый [server]. Для нового сервера создаются стандартные категории с каналами, роли с
     * привилегиями, участник сервера из владельца и так далее.
     */
    suspend fun saveServer(server: Server): Server

    /**
     * Возвращает сервер с таким [id].
     */
    suspend fun findServer(id: UUID): Server

    /**
     * Удалить сервер с таким [id] его владельцем с идентификатором [ownerId].
     */
    suspend fun deleteServer(ownerId: UUID, id: UUID)
}