package ru.blimfy.server.usecase.role

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.server.db.entity.Privilege
import ru.blimfy.server.db.entity.Role
import ru.blimfy.server.db.repository.RoleRepository
import ru.blimfy.server.usecase.exception.ServerErrors.ROLE_BY_ID_NOT_FOUND
import ru.blimfy.server.usecase.privilege.PrivilegeService
import ru.blimfy.common.enumeration.PrivilegeTypes.entries as privileges

/**
 * Реализация интерфейса для работы с ролями сервера.
 *
 * @property roleRepo репозиторий для работы с ролями в БД.
 * @property privilegeService сервис для работы с привилегиями ролей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class RoleServiceImpl(
    private val roleRepo: RoleRepository,
    private val privilegeService: PrivilegeService,
) : RoleService {
    override suspend fun createRole(role: Role) =
        roleRepo.save(role).apply {
            // Для новой роли создаём набор стандартных привилегий.
            privileges.onEach { privilegeService.savePrivilege(Privilege(id, it, it.defaultGranted)) }
        }

    override suspend fun modifyRole(role: Role) =
        roleRepo.save(role)

    override suspend fun findDefaultServerRole(serverId: UUID) =
        roleRepo.findAllByServerIdAndBasicIsTrue(serverId)

    override suspend fun findRole(id: UUID) = roleRepo.findById(id)
        ?: throw NotFoundException(ROLE_BY_ID_NOT_FOUND.msg.format(id))

    companion object {
        /**
         * Название дефолтной роли.
         */
        const val DEFAULT_ROLE_NAME = "Все"
    }
}