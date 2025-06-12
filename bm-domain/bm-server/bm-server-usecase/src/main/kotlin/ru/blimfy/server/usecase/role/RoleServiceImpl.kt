package ru.blimfy.server.usecase.role

import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.server.db.entity.Role
import ru.blimfy.server.db.repository.RoleRepository
import ru.blimfy.server.usecase.exception.ServerErrors.ROLE_BY_ID_NOT_FOUND
import java.util.*

/**
 * Реализация интерфейса для работы с ролями сервера.
 *
 * @property roleRepo репозиторий для работы с ролями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class RoleServiceImpl(private val roleRepo: RoleRepository) : RoleService {
    override suspend fun createRole(role: Role) =
        roleRepo.save(role)

    override suspend fun modifyRole(role: Role) =
        roleRepo.save(role)

    override suspend fun findDefaultServerRole(serverId: UUID) =
        roleRepo.findDefaultServerRole(serverId)

    override suspend fun findServerRole(id: UUID, serverId: UUID) =
        roleRepo.findByIdAndServerId(id, serverId)
            ?: throw NotFoundException(ROLE_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findRole(id: UUID) =
        roleRepo.findById(id)
            ?: throw NotFoundException(ROLE_BY_ID_NOT_FOUND.msg.format(id))

    override fun findServerRoles(serverId: UUID) =
        roleRepo.findAllByServerId(serverId)
}