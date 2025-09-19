package tech.ruslo.domain.server.usecase.member

import java.util.UUID
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import tech.ruslo.common.exception.NotFoundException
import tech.ruslo.domain.server.db.entity.Member
import tech.ruslo.domain.server.db.repository.MemberRepository
import tech.ruslo.domain.server.db.repository.MemberRoleRepository
import tech.ruslo.domain.server.db.repository.RoleRepository
import tech.ruslo.server.usecase.exception.ServerErrors.MEMBER_NOT_FOUND

/**
 * Реализация интерфейса для работы с участниками сервера.
 *
 * @property memberRepo репозиторий для работы с участниками серверов в БД.
 * @property roleRepo репозиторий для работы с ролями в БД.
 * @property memberRoleRepo репозиторий для работы с сущностями участников ролей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberServiceImpl(
    private val memberRepo: MemberRepository,
    private val roleRepo: RoleRepository,
    private val memberRoleRepo: MemberRoleRepository,
) : MemberService {
    override suspend fun createMember(serverId: UUID, userId: UUID) =
        memberRepo.save(Member(serverId, userId)).apply { fetchRoles() }

    override suspend fun findMember(serverId: UUID, userId: UUID, withRoles: Boolean) =
        findMemberOrThrow(serverId, userId).apply {
            if (withRoles) {
                fetchRoles()
            }
        }

    override suspend fun getCountServerMembers(serverId: UUID) =
        memberRepo.countByServerId(serverId)

    override fun findUserMembers(userId: UUID) =
        memberRepo.findAllByUserId(userId).onEach {
            it.fetchRoles()
        }

    override fun findServerMembers(serverId: UUID) =
        memberRepo.findAllByServerId(serverId).onEach { it.fetchRoles() }

    override fun findRoleMembers(roleId: UUID, serverId: UUID) =
        memberRoleRepo.findAllByRoleId(roleId)
            .map { findMemberOrThrow(serverId, it.memberId) }

    override suspend fun setNick(serverId: UUID, userId: UUID, nick: String?) =
        findMemberOrThrow(serverId, userId)
            .apply { this.nick = nick }
            .let { memberRepo.save(it) }
            .apply { fetchRoles() }

    override suspend fun deleteServerMember(memberId: UUID, serverId: UUID) =
        memberRepo.deleteByIdAndServerId(memberId, serverId)

    override suspend fun deleteUserMember(userId: UUID, serverId: UUID) =
        memberRepo.deleteByUserIdAndServerId(userId, serverId)

    override suspend fun deleteServerMembers(serverId: UUID) =
        memberRepo.deleteAllByServerId(serverId)

    /**
     * Возвращает участника с [userId] для сервера с [serverId] или выбрасывает исключение, если он не найден.
     */
    private suspend fun findMemberOrThrow(serverId: UUID, userId: UUID) =
        memberRepo.findByServerIdAndUserId(serverId, userId)
            ?: throw NotFoundException(MEMBER_NOT_FOUND.msg)

    /**
     * Загружает роли для участника сервера.
     */
    private suspend fun Member.fetchRoles() {
        roles = memberRoleRepo.findAllByMemberId(id)
            .mapNotNull { roleRepo.findById(it.roleId) }
            .toList()
    }
}