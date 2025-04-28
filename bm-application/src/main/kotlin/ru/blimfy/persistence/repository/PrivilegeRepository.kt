package ru.blimfy.persistence.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.Privilege

/**
 * Репозиторий для работы с сущностью привилегии роли в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface PrivilegeRepository : CoroutineCrudRepository<Privilege, UUID> {
    /**
     * Возвращает все сущности привилегий для роли с идентификатором [roleId].
     */
    fun findAllByRoleId(roleId: UUID): Flow<Privilege>
}