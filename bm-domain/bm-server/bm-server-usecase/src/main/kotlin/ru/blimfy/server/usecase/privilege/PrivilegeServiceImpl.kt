package ru.blimfy.server.usecase.privilege

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.server.db.entity.Privilege
import ru.blimfy.server.db.repository.PrivilegeRepository

/**
 * Реализация интерфейса для работы с привилегиями ролей.
 *
 * @property privilegeRepo репозиторий для работы с привилегиями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class PrivilegeServiceImpl(private val privilegeRepo: PrivilegeRepository) : PrivilegeService {
    override suspend fun savePrivilege(privilege: Privilege) = privilegeRepo.save(privilege)

    override fun findRolePrivileges(roleId: UUID) = privilegeRepo.findAllByRoleId(roleId)
}