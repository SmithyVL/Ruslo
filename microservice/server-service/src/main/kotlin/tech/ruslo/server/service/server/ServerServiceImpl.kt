package tech.ruslo.server.service.server

import kotlinx.coroutines.flow.mapNotNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.ruslo.server.database.entity.Member
import tech.ruslo.server.database.entity.Role
import tech.ruslo.server.database.entity.Server
import tech.ruslo.server.database.repository.ServerRepository
import tech.ruslo.server.dto.role.enumerations.Permissions
import tech.ruslo.server.dto.role.enumerations.Permissions.entries
import tech.ruslo.server.service.member.MemberService
import tech.ruslo.server.service.role.RoleService

/**
 * Реализация интерфейса для работы с серверами пользователей.
 *
 * @property serverRepository репозиторий для работы с сущностями серверов в БД.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @property defaultPermissions дефолтные разрешения для роли сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerServiceImpl(
    private val serverRepository: ServerRepository,
    private val roleService: RoleService,
    private val memberService: MemberService,
) : ServerService {
    private val defaultPermissions = entries
        .filter(Permissions::defaultGranted)
        .sumOf(Permissions::bitMask)
        .toString()

    @Transactional
    override suspend fun saveServer(server: Server) =
        if (server.id == 0L) {
            serverRepository.save(server).apply {
                val defaultRole = saveDefaultRole(id)
                val ownerMember = saveOwnerMember(id, ownerId)
                roleService.addMember(defaultRole.id, ownerMember.id)
                defaultRoleId = defaultRole.id
            }
        } else {
            serverRepository.save(server)
        }

    override fun findUserServers(userId: Long) =
        memberService.findUserMembers(userId).mapNotNull { serverRepository.findById(it.serverId) }

    /**
     * Возвращает сохранённую стандартную роль сервера с [serverId].
     */
    private suspend fun saveDefaultRole(serverId: Long) =
        Role(serverId)
            .apply { permissions = defaultPermissions }
            .let { roleService.saveRole(it) }

    /**
     * Возвращает сохранённого участника сервера с [serverId] для его владельца с [ownerId].
     */
    private suspend fun saveOwnerMember(serverId: Long, ownerId: Long) =
        Member(serverId, ownerId)
            .let { memberService.saveMember(it) }
}