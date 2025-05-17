package ru.blimfy.server.usecase.member

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.server.db.entity.Member

/**
 * Интерфейс для работы с участниками сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberService {
    /**
     * Возвращает нового или обновлённого [member].
     */
    suspend fun saveMember(member: Member): Member

    /**
     * Изменяет [newNick] участника с [userId] с сервера с [serverId].
     */
    suspend fun setNick(serverId: UUID, userId: UUID, newNick: String? = null): Member

    /**
     * Возвращает участника сервера с идентификатором [serverId] для пользователя с идентификатором [userId].
     */
    suspend fun findServerMember(serverId: UUID, userId: UUID): Member

    /**
     * Возвращает участника с идентификатором [id].
     */
    suspend fun findMember(id: UUID): Member

    /**
     * Возвращает количество участников сервера с идентификатором [serverId].
     */
    suspend fun getCountServerMembers(serverId: UUID): Long

    /**
     * Возвращает все участия пользователя с идентификатором [userId] на различных серверах.
     */
    fun findUserMembers(userId: UUID): Flow<Member>

    /**
     * Возвращает всех участников сервера с идентификатором [serverId].
     */
    fun findServerMembers(serverId: UUID): Flow<Member>

    /**
     * Удаляет участника с идентификатором [memberId] с сервера с идентификатором [serverId].
     */
    suspend fun deleteServerMember(memberId: UUID, serverId: UUID)

    /**
     * Удаляет участника пользователя с идентификатором [userId] с сервера с идентификатором [serverId].
     */
    suspend fun deleteUserMember(userId: UUID, serverId: UUID)

    /**
     * Удаляет всех участников сервера с идентификатором [serverId].
     */
    suspend fun deleteServerMembers(serverId: UUID)
}