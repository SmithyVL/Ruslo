package ru.blimfy.services.member.role

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.persistence.entity.MemberRole
import ru.blimfy.persistence.repository.MemberRoleRepository

/**
 * Реализация интерфейса для работы с ролями участников сервера.
 *
 * @property memberRoleRepo репозиторий для работы с ролями участников серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberRoleServiceImpl(private val memberRoleRepo: MemberRoleRepository) : MemberRoleService {
    override suspend fun saveRoleToMember(memberRole: MemberRole) = memberRoleRepo.save(memberRole)

    override fun findMemberRoles(memberId: UUID) = memberRoleRepo.findAllByMemberId(memberId)

    override fun findRoleMembers(roleId: UUID) = memberRoleRepo.findAllByRoleId(roleId)

    override suspend fun deleteMemberRole(memberId: UUID, roleId: UUID) =
        memberRoleRepo.deleteByMemberIdAndRoleId(memberId = memberId, roleId = roleId)
}