package ru.blimfy.server.usecase.member.role

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.server.db.entity.MemberRole

/**
 * Интерфейс для работы с ролями участников сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberRoleService {
    /**
     * Возвращает новую или обновлённую [memberRole].
     */
    suspend fun saveRoleToMember(memberRole: MemberRole): MemberRole

    /**
     * Возвращает все роли участника сервера с идентификатором [memberId].
     */
    fun findMemberRoles(memberId: UUID): Flow<MemberRole>

    /**
     * Возвращает всех участников роли с идентификатором [roleId].
     */
    fun findRoleMembers(roleId: UUID): Flow<MemberRole>

    /**
     * Удаляет роль с идентификатором [roleId] у участника сервера с идентификатором [memberId].
     */
    suspend fun deleteMemberRole(memberId: UUID, roleId: UUID)
}