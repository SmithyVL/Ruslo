package tech.ruslo.server.service.role

import org.springframework.stereotype.Service
import tech.ruslo.server.database.entity.MemberRole
import tech.ruslo.server.database.entity.Role
import tech.ruslo.server.database.repository.MemberRoleRepository
import tech.ruslo.server.database.repository.RoleRepository

/**
 * Реализация интерфейса для работы с ролями.
 *
 * @property roleRepository репозиторий для работы с сущностями ролей.
 * @property memberRoleRepository репозиторий для работы с сущностями участников ролей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository,
    private val memberRoleRepository: MemberRoleRepository,
) : RoleService {
    override suspend fun saveRole(role: Role) =
        roleRepository.save(role)

    override fun saveRoles(roles: List<Role>) =
        roleRepository.saveAll(roles)

    override suspend fun addMember(roleId: Long, memberId: Long) {
        memberRoleRepository.save(MemberRole(memberId, roleId))
    }
}