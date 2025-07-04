package ru.blimfy.domain.server.usecase.role

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.domain.server.api.dto.role.ModifyRoleDto
import ru.blimfy.domain.server.api.dto.role.NewRoleDto
import ru.blimfy.domain.server.api.dto.role.RolePositionDto
import ru.blimfy.domain.server.db.entity.Member
import ru.blimfy.domain.server.db.entity.Role

/**
 * Интерфейс для работы с ролями сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface RoleService {
    /**
     * Возвращает роль, созданную по информации из [newRoleDto], для сервера [serverId].
     */
    suspend fun createRole(newRoleDto: NewRoleDto, serverId: UUID): Role

    /**
     * Возвращает роль с [id] для сервера с [serverId].
     */
    suspend fun findRole(id: UUID, serverId: UUID): Role

    /**
     * Возвращает роли сервера с [serverId].
     */
    fun findRoles(serverId: UUID): Flow<Role>

    /**
     * Возвращает роли участника с [memberId] сервера с [serverId].
     */
    fun findMemberRoles(memberId: UUID, serverId: UUID): Flow<Role>

    /**
     * Возвращает роль с [id], обновлённую по информации из [modifyRoleDto], для сервера [serverId].
     */
    suspend fun modifyRole(id: UUID, modifyRoleDto: ModifyRoleDto, serverId: UUID): Role

    /**
     * Возвращает роли, с обновлёнными позициями по информации из [positionDtos], для сервера с [serverId].
     */
    suspend fun modifyPositions(serverId: UUID, positionDtos: List<RolePositionDto>): Flow<Role>

    /**
     * Возвращает нового участника с [memberId] у роли с [id] для сервера с [serverId].
     */
    suspend fun addMember(id: UUID, memberId: UUID, serverId: UUID): Member

    /**
     * Возвращает нового участника с [memberId] у дефолтной роли сервера с [serverId].
     */
    suspend fun addMemberToDefault(memberId: UUID, serverId: UUID): Member

    /**
     * Возвращает новых участников с [memberIds] у роли с [id] для сервера с [serverId].
     */
    suspend fun addMembers(id: UUID, memberIds: List<UUID>, serverId: UUID): Flow<Member>

    /**
     * Возвращает участника с [userId] с новым списком ролей с [roleIds] для сервера с [serverId].
     */
    suspend fun changeMemberRoles(roleIds: List<UUID>, serverId: UUID, userId: UUID): Member

    /**
     * Удаляет роль с [id] для сервера с [serverId].
     */
    suspend fun deleteRole(id: UUID, serverId: UUID)

    /**
     * Удаляет роли у участника с [memberId].
     */
    suspend fun deleteMemberRoles(memberId: UUID)
}