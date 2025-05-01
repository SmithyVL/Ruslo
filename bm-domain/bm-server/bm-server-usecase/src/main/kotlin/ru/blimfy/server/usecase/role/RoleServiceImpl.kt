package ru.blimfy.server.usecase.role

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.server.db.entity.Role
import ru.blimfy.server.db.repository.RoleRepository
import ru.blimfy.server.usecase.privilege.PrivilegeService

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
    @Transactional
    override suspend fun createRole(role: Role) =
        roleRepo.save(role).apply {
            // Для новой роли создаём набор стандартных привилегий.
            privilegeService.initDefaultPrivileges(this.id)
        }

    override suspend fun modifyRole(role: Role) =
        roleRepo.save(role)

    override suspend fun findDefaultServerRole(serverId: UUID) =
        roleRepo.findAllByServerIdAndBasicIsTrue(serverId)

    companion object {
        /**
         * Название дефолтной роли.
         */
        const val DEFAULT_ROLE_NAME = "Все"
    }
}