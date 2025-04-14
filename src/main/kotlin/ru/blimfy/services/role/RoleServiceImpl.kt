package ru.blimfy.services.role

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.persistence.entity.Role
import ru.blimfy.persistence.repository.RoleRepository
import ru.blimfy.services.privilege.PrivilegeService

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
    override suspend fun saveRole(role: Role) =
        roleRepo.save(role).apply { privilegeService.initDefaultPrivileges(this.id) }

    override suspend fun findDefaultServerRole(serverId: UUID) =
        roleRepo.findAllByServerIdAndDefaultIsTrue(serverId)

    companion object {
        /**
         * Название дефолтной роли.
         */
        const val DEFAULT_ROLE_NAME = "Все"
    }
}