package ru.blimfy.server.usecase.role

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.server.db.entity.Role
import ru.blimfy.server.db.repository.RoleRepository
import ru.blimfy.server.usecase.exception.ServerErrors.ROLE_BY_ID_NOT_FOUND

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
        roleRepo.findAllByServerIdAndPosition(serverId, 0)

    override suspend fun findRole(id: UUID) = roleRepo.findById(id)
        ?: throw NotFoundException(ROLE_BY_ID_NOT_FOUND.msg.format(id))

    companion object {
        /**
         * Название дефолтной роли.
         */
        const val DEFAULT_ROLE_NAME = "@все"
    }
}