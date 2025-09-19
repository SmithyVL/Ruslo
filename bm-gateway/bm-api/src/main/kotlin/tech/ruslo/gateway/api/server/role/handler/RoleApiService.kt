package tech.ruslo.gateway.api.server.role.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import tech.ruslo.domain.server.api.dto.role.ModifyRoleDto
import tech.ruslo.domain.server.api.dto.role.NewRoleDto
import tech.ruslo.domain.server.api.dto.role.RolePositionDto
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.dto.member.MemberDto
import tech.ruslo.gateway.dto.role.RoleDto

/**
 * Интерфейс для работы с обработкой запросов о ролях серверов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface RoleApiService {
    /**
     * Возвращает роль, созданную по информации из [newRoleDto] для сервера с [serverId], которую создаёт [user].
     */
    suspend fun createRole(serverId: UUID, newRoleDto: NewRoleDto, user: User): RoleDto

    /**
     * Возвращает идентификаторы пользователей участников роли с [id] для сервера с [serverId, которых ищёт [user].
     */
    suspend fun findMemberIds(id: UUID, serverId: UUID, user: User): Flow<UUID>

    /**
     * Возвращает роль с [id], обновлённую по информации из [modifyRoleDto] для сервера с [serverId], которую обновляет
     * [user].
     */
    suspend fun modifyRole(id: UUID, serverId: UUID, modifyRoleDto: ModifyRoleDto, user: User): RoleDto

    /**
     * Возвращает участников сервера с [serverId], с добавленной ролью с [id], которую обновляет [user].
     */
    suspend fun addRoleMembers(id: UUID, serverId: UUID, memberIds: List<UUID>, user: User): Flow<MemberDto>

    /**
     * Возвращает роли сервера с [serverId], с обновлёнными позициями из [positionDtos], которые обновляет [user].
     */
    suspend fun modifyPositions(serverId: UUID, positionDtos: List<RolePositionDto>, user: User): Flow<RoleDto>

    /**
     * Удаляет роль с [id] для сервера с [serverId], которую удаляет [user].
     */
    suspend fun deleteRole(id: UUID, serverId: UUID, user: User)
}