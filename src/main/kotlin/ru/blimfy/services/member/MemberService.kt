package ru.blimfy.services.member

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.common.dto.MemberDto

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
    suspend fun saveMember(member: MemberDto): MemberDto

    /**
     * Возвращает участника сервера с идентификатором [serverId] для пользователя с идентификатором [userId].
     */
    suspend fun findServerMember(userId: UUID, serverId: UUID): MemberDto

    /**
     * Возвращает все участия пользователя с идентификатором [userId] на различных серверах.
     */
    fun findUserMembers(userId: UUID): Flow<MemberDto>

    /**
     * Возвращает всех участников сервера с идентификатором [serverId].
     */
    fun findServerMembers(serverId: UUID): Flow<MemberDto>

    /**
     * Удаляет участника с идентификатором [memberId] с сервера с идентификатором [serverId].
     */
    suspend fun deleteServerMember(memberId: UUID, serverId: UUID)

    /**
     * Удаляет всех участников сервера с идентификатором [serverId].
     */
    suspend fun deleteServerMembers(serverId: UUID)
}