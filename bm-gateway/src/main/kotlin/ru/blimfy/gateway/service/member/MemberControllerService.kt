package ru.blimfy.gateway.service.member

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.server.member.MemberDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberControllerService {
    /**
     * Возвращает участников сервера с [serverId], которых хочет получить [user].
     */
    suspend fun findMembers(serverId: UUID, user: User): Flow<MemberDto>

    /**
     * [user] удаляет пользователя с [userId] с сервера с [serverId].
     */
    suspend fun removeMember(serverId: UUID, userId: UUID, user: User)

    /**
     * Возвращает обновлённого участника сервера с [serverId] с новым [nick] для пользователя с [userId], которого
     * обновляет [user].
     */
    suspend fun changeMemberNick(serverId: UUID, userId: UUID, nick: String? = null, user: User): MemberDto

    /**
     * Возвращает обновлённого участника сервера с [serverId] с новым [nick], который для себя меняет [user].
     */
    suspend fun changeCurrentMemberNick(serverId: UUID, nick: String? = null, user: User): MemberDto
}