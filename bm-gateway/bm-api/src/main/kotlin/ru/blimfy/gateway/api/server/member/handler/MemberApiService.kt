package ru.blimfy.gateway.api.server.member.handler

import java.util.UUID
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.dto.member.MemberDto

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberApiService {
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
     * Возвращает обновлённого участника сервера с [serverId] с новыми [roleIds] для пользователя с [userId], которого
     * обновляет [user].
     */
    suspend fun changeMemberRoles(serverId: UUID, userId: UUID, roleIds: List<UUID>, user: User): MemberDto

    /**
     * Возвращает обновлённого участника сервера с [serverId] с новым [nick], который для себя меняет [user].
     */
    suspend fun changeCurrentMemberNick(serverId: UUID, nick: String? = null, user: User): MemberDto
}