package tech.ruslo.domain.server.usecase.server

import java.util.UUID
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.ruslo.common.exception.NotFoundException
import tech.ruslo.domain.server.api.dto.role.NewRoleDto
import tech.ruslo.domain.server.api.dto.server.ModifyServerDto
import tech.ruslo.domain.server.api.dto.server.NewServerDto
import tech.ruslo.domain.server.db.entity.ROLE_DEFAULT_NAME
import tech.ruslo.domain.server.db.entity.Server
import tech.ruslo.domain.server.db.repository.ServerRepository
import tech.ruslo.domain.server.usecase.member.MemberService
import tech.ruslo.domain.server.usecase.role.RoleService
import tech.ruslo.domain.server.usecase.role.RoleServiceImpl.Companion.calculateDefaultPermissions
import tech.ruslo.server.usecase.exception.ServerErrors.SERVER_NOT_FOUND

/**
 * Реализация интерфейса для работы с серверами пользователей.
 *
 * @property repo репозиторий для работы с сущностями серверов в БД.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerServiceImpl(
    private val repo: ServerRepository,
    private val roleService: RoleService,
    private val memberService: MemberService,
) : ServerService {
    @Transactional
    override suspend fun createServer(newServerDto: NewServerDto, userId: UUID) =
        repo.save(newServerDto.toEntity(userId)).apply {
            roles = NewRoleDto(ROLE_DEFAULT_NAME, calculateDefaultPermissions())
                .let { roleService.createRole(it, id) }
                .apply {
                    val memberId = memberService.createMember(serverId, ownerId).id
                    roleService.addMember(id, memberId, serverId)
                }
                .let { listOf(it) }
        }

    override suspend fun findServer(id: UUID, withRoles: Boolean, withMembers: Boolean) =
        findServerOrThrow(id).apply {
            if (withRoles) {
                roles = roleService.findRoles(id).toList()
            }

            if (withMembers) {
                members = memberService.findServerMembers(id).toList()
            }
        }

    override fun findUserServers(userId: UUID) =
        memberService.findUserMembers(userId)
            .map { findServer(it.serverId, withRoles = true, withMembers = true) }

    override suspend fun modifyServer(id: UUID, modifyServerDto: ModifyServerDto) =
        modifyServer(id) {
            modifyServerDto.toEntity(it)
        }

    override suspend fun modifyOwner(id: UUID, userId: UUID) =
        modifyServer(id) {
            it.ownerId = userId
            it
        }

    @Transactional
    override suspend fun addNewMember(id: UUID, userId: UUID) =
        memberService.createMember(id, userId).let {
            roleService.addMemberToDefault(it.id, id)
        }

    override suspend fun deleteServer(id: UUID) =
        repo.deleteById(id)

    /**
     * Возвращает сервер с [id], обновлённый с использованием [callback].
     */
    private suspend fun modifyServer(id: UUID, callback: (Server) -> Server) =
        callback(findServer(id))
            .let { repo.save(it) }

    /**
     * Возвращает сервер с [id] или выбрасывает исключение, если он не найден.
     */
    private suspend fun findServerOrThrow(id: UUID) =
        repo.findById(id) ?: throw NotFoundException(SERVER_NOT_FOUND.msg.format(id))
}

/**
 * Возвращает сущность из DTO представления нового сервера для владельца с [ownerId].
 */
fun NewServerDto.toEntity(ownerId: UUID) = Server(ownerId, name).apply { icon = this@toEntity.icon }

/**
 * Возвращает сущность из DTO представления обновлённого [server].
 */
fun ModifyServerDto.toEntity(server: Server) =
    Server(server.ownerId, name).apply {
        icon = this@toEntity.icon
        description = this@toEntity.description
        bannerColor = this@toEntity.bannerColor
        createdDate = server.createdDate
        id = server.id
    }