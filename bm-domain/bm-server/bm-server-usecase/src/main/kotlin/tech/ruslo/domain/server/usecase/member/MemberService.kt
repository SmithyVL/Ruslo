package tech.ruslo.domain.server.usecase.member

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import tech.ruslo.domain.server.db.entity.Member

/**
 * Интерфейс для работы с участниками сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberService {
    /**
     * Возвращает нового участника сервера с [serverId] для пользователя с [userId].
     */
    suspend fun createMember(serverId: UUID, userId: UUID): Member

    /**
     * Возвращает участника сервера с [serverId] для пользователя с [userId]. В ответ включается информация о ролях,
     * если флаг [withRoles] установлен в true.
     */
    suspend fun findMember(serverId: UUID, userId: UUID, withRoles: Boolean = false): Member

    /**
     * Возвращает количество участников сервера с [serverId].
     */
    suspend fun getCountServerMembers(serverId: UUID): Long

    /**
     * Возвращает все участия пользователя с [userId] на различных серверах.
     */
    fun findUserMembers(userId: UUID): Flow<Member>

    /**
     * Возвращает всех участников сервера с [serverId]. В ответ включается вся информация с ролями.
     */
    fun findServerMembers(serverId: UUID): Flow<Member>

    /**
     * Возвращает участников роли с [roleId] сервера с [serverId]. В ответ включается только основная информация.
     */
    fun findRoleMembers(roleId: UUID, serverId: UUID): Flow<Member>

    /**
     * Изменяет [nick] участника с [userId] с сервера с [serverId].
     */
    suspend fun setNick(serverId: UUID, userId: UUID, nick: String? = null): Member

    /**
     * Удаляет участника с [memberId] с сервера с [serverId].
     */
    suspend fun deleteServerMember(memberId: UUID, serverId: UUID)

    /**
     * Удаляет участника пользователя с [userId] с сервера с [serverId].
     */
    suspend fun deleteUserMember(userId: UUID, serverId: UUID)

    /**
     * Удаляет всех участников сервера с [serverId].
     */
    suspend fun deleteServerMembers(serverId: UUID)
}