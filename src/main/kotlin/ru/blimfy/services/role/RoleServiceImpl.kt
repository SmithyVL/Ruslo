package ru.blimfy.services.role

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.persistence.entity.Role
import ru.blimfy.persistence.repository.PrivilegeRepository
import ru.blimfy.persistence.repository.RoleRepository

/**
 * Реализация интерфейса для работы с ролями сервера.
 *
 * @property roleRepo репозиторий для работы с паролями в БД.
 * @property privilegeRepo сервис для работы с привилегиями ролей.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class RoleServiceImpl(
    private val roleRepo: RoleRepository,
    private val privilegeRepo: PrivilegeRepository,
) : RoleService {
    @Transactional
    override suspend fun saveRole(role: Role) = roleRepo.save(role)

    companion object {
        /**
         * Название дефолтной роли для всех участников сервера.
         */
        const val ROLE_FOR_ALL_NAME = "Все"
    }
}