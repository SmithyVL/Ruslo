package ru.blimfy.server.usecase.privilege

import java.util.UUID
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.server.db.entity.Privilege
import ru.blimfy.server.db.repository.PrivilegeRepository
import ru.blimfy.common.enumeration.PrivilegeTypes.entries as privileges

/**
 * Реализация интерфейса для работы с привилегиями ролей.
 *
 * @property privilegeRepo репозиторий для работы с привилегиями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class PrivilegeServiceImpl(private val privilegeRepo: PrivilegeRepository) : PrivilegeService {
    @Transactional
    override suspend fun initDefaultPrivileges(roleId: UUID) {
        privileges.asFlow()
            .onEach { privilegeRepo.save(Privilege(roleId, it, it.defaultGranted)) }
            .collect()
    }
}