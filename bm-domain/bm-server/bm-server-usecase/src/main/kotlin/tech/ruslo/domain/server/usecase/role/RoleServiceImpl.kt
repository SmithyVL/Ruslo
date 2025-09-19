package tech.ruslo.domain.server.usecase.role

import java.util.UUID
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import tech.ruslo.common.exception.NotFoundException
import tech.ruslo.domain.server.api.dto.role.ModifyRoleDto
import tech.ruslo.domain.server.api.dto.role.NewRoleDto
import tech.ruslo.domain.server.api.dto.role.RolePositionDto
import tech.ruslo.domain.server.api.enumerations.Permissions
import tech.ruslo.domain.server.api.enumerations.Permissions.entries
import tech.ruslo.domain.server.db.entity.MemberRole
import tech.ruslo.domain.server.db.entity.Role
import tech.ruslo.domain.server.db.repository.MemberRoleRepository
import tech.ruslo.domain.server.db.repository.RoleRepository
import tech.ruslo.domain.server.usecase.member.MemberService
import tech.ruslo.server.usecase.exception.ServerErrors.DEFAULT_ROLE_NOT_FOUND
import tech.ruslo.server.usecase.exception.ServerErrors.ROLE_NOT_FOUND

/**
 * Реализация интерфейса для работы с ролями.
 *
 * @property repo репозиторий для работы с сущностями ролей.
 * @property memberRoleRepo репозиторий для работы с сущностями участников ролей.
 * @property memberService сервис для работы с участниками серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class RoleServiceImpl(
    private val repo: RoleRepository,
    private val memberRoleRepo: MemberRoleRepository,
    private val memberService: MemberService,
) : RoleService {
    override suspend fun createRole(newRoleDto: NewRoleDto, serverId: UUID) =
        repo.save(newRoleDto.toEntity(serverId))

    override suspend fun findRole(id: UUID, serverId: UUID) =
        findRoleOrThrow(id, serverId)

    override fun findRoles(serverId: UUID) =
        repo.findAllByServerId(serverId)

    override fun findMemberRoles(memberId: UUID, serverId: UUID) =
        memberRoleRepo.findAllByMemberId(memberId)
            .map { findRole(it.roleId, serverId) }

    override suspend fun modifyRole(id: UUID, modifyRoleDto: ModifyRoleDto, serverId: UUID) =
        findRoleOrThrow(id, serverId)
            .let { repo.save(modifyRoleDto.toEntity(it)) }

    override suspend fun modifyPositions(serverId: UUID, positionDtos: List<RolePositionDto>) =
        positionDtos.asFlow()
            .map { findRoleOrThrow(it.id, serverId).apply { position = it.position } }
            .map { repo.save(it) }

    override suspend fun addMember(id: UUID, memberId: UUID, serverId: UUID) =
        saveMemberRole(memberId, id)
            .let { memberService.findMember(it.memberId, serverId, true) }

    override suspend fun addMemberToDefault(memberId: UUID, serverId: UUID) =
        addMember(memberId, findDefaultRoleOrThrow(serverId).id, serverId)

    override suspend fun addMembers(id: UUID, memberIds: List<UUID>, serverId: UUID) =
        findRole(id, serverId)
            .let { memberIds.asFlow() }
            .map { addMember(id, it, serverId) }

    override suspend fun changeMemberRoles(roleIds: List<UUID>, serverId: UUID, userId: UUID) =
        memberService.findMember(serverId, userId).apply {
            deleteMemberRoles(this.id)
            roleIds.forEach { saveMemberRole(it, id) }
        }

    override suspend fun deleteRole(id: UUID, serverId: UUID) =
        repo.deleteByIdAndServerId(id, serverId)

    override suspend fun deleteMemberRoles(memberId: UUID) =
        memberRoleRepo.deleteAllByMemberId(memberId)

    /**
     * Возвращает роль с [id] для сервера с [serverId] или выбрасывает исключение, если она не найдена.
     */
    private suspend fun findRoleOrThrow(id: UUID, serverId: UUID) =
        repo.findByIdAndServerId(id, serverId) ?: throw NotFoundException(ROLE_NOT_FOUND.msg.format(id))

    /**
     * Возвращает стандартную роль для сервера с [serverId] или выбрасывает исключение, если она не найдена.
     */
    private suspend fun findDefaultRoleOrThrow(serverId: UUID) =
        repo.findFirstServerRole(serverId)
            ?: throw NotFoundException(DEFAULT_ROLE_NOT_FOUND.msg.format(serverId))

    /**
     * Возвращает связь роли с [id] с участником сервера с [memberId].
     */
    private suspend fun saveMemberRole(id: UUID, memberId: UUID) =
        memberRoleRepo.save(MemberRole(memberId, id))

    companion object {
        /**
         * Возвращает дефолтное значение разрешений.
         */
        fun calculateDefaultPermissions() =
            entries
                .filter(Permissions::defaultGranted)
                .sumOf(Permissions::bitMask)
                .toString()
    }
}

/**
 * Возвращает сущность из DTO представления новой роли.
 */
fun NewRoleDto.toEntity(serverId: UUID) = Role(serverId).apply {
    name = this@toEntity.name
    permissions = this@toEntity.permissions
}

/**
 * Возвращает сущность [role] из DTO представления с обновлённой информацией.
 */
fun ModifyRoleDto.toEntity(role: Role) = Role(role.serverId).apply {
    name = this@toEntity.name
    permissions = this@toEntity.permissions
    color = this@toEntity.color
    mentionable = this@toEntity.mentionable
    position = role.position
    createdDate = role.createdDate
}