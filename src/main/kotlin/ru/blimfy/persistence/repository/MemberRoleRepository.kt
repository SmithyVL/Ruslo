package ru.blimfy.persistence.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.MemberRole

/**
 * Репозиторий для работы с сущностью роли участника сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MemberRoleRepository : CoroutineCrudRepository<MemberRole, UUID> {
    /**
     * Возвращает все сущности ролей для участника с идентификатором [memberId].
     */
    fun findAllByMemberId(memberId: UUID): Flow<MemberRole>
}