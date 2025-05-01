package ru.blimfy.server.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.server.db.entity.MemberRole

/**
 * Репозиторий для работы с сущностью роли участника сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MemberRoleRepository : CoroutineCrudRepository<MemberRole, UUID> {
    /**
     * Возвращает все сущности ролей участников для участника с идентификатором [memberId].
     */
    fun findAllByMemberId(memberId: UUID): Flow<MemberRole>

    /**
     * Возвращает все сущности ролей участников для роли с идентификатором [roleId].
     */
    fun findAllByRoleId(roleId: UUID): Flow<MemberRole>

    /**
     * Удаляет сущность роли с идентификатором [roleId] у участника с идентификатором [memberId].
     */
    suspend fun deleteByMemberIdAndRoleId(memberId: UUID, roleId: UUID)
}