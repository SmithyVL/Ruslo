package tech.ruslo.server.service.member

import kotlinx.coroutines.flow.Flow
import tech.ruslo.server.database.entity.Member

/**
 * Интерфейс для работы с участниками сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberService {
    /**
     * Возвращает обновлённого [member].
     */
    suspend fun saveMember(member: Member): Member

    /**
     * Возвращает участников серверов пользователя с идентификатором [userId].
     */
    fun findUserMembers(userId: Long): Flow<Member>

    /**
     * Удаляет участника для пользователя с [userId] и сервера с [serverId].
     */
    suspend fun removeMember(userId: Long, serverId: Long)
}