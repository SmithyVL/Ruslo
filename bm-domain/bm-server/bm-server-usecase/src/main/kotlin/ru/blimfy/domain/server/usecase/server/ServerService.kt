package ru.blimfy.domain.server.usecase.server

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.domain.server.api.dto.server.ModifyServerDto
import ru.blimfy.domain.server.api.dto.server.NewServerDto
import ru.blimfy.domain.server.db.entity.Member
import ru.blimfy.domain.server.db.entity.Server

/**
 * Интерфейс для работы с серверами пользователя.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ServerService {
    /**
     * Возвращает сервер, созданный по информации из [newServerDto], с владельцем-пользователем с [userId]. В ответ
     * дополнительно включаются роли сервера.
     */
    suspend fun createServer(newServerDto: NewServerDto, userId: UUID): Server

    /**
     * Возвращает сервер с [id]. В ответ включается информация о ролях, если флаг [withRoles] установлен в true, и об
     * участниках, если флаг [withMembers] установлен в true.
     */
    suspend fun findServer(id: UUID, withRoles: Boolean = false, withMembers: Boolean = false): Server

    /**
     * Возвращает сервера пользователя с [userId]. В ответ включается вся информация с ролями и участниками.
     */
    fun findUserServers(userId: UUID): Flow<Server>

    /**
     * Возвращает сервер с [id], обновлённый по информации из [modifyServerDto]. В ответ включается только основная
     * информация.
     */
    suspend fun modifyServer(id: UUID, modifyServerDto: ModifyServerDto): Server

    /**
     * Возвращает сервер с [id], с новым владельцем-пользователем с [userId]. В ответ включается только основная
     * информация.
     */
    suspend fun modifyOwner(id: UUID, userId: UUID): Server

    /**
     * Возвращает нового пользователя с [userId] на сервер с [id].
     */
    suspend fun addNewMember(id: UUID, userId: UUID): Member

    /**
     * Удаляет сервер с [id].
     */
    suspend fun deleteServer(id: UUID)
}