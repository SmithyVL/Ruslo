package tech.ruslo.server.database.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.server.database.entity.MemberRole

/**
 * Репозиторий для работы с сущностью роли участника сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MemberRoleRepository : CoroutineCrudRepository<MemberRole, Long> {
    /**
     * Возвращает все сущности ролей участников для участника с идентификатором [memberId].
     */
    fun findAllByMemberId(memberId: Long): Flow<MemberRole>

    /**
     * Возвращает все сущности ролей участников для роли с идентификатором [roleId].
     */
    fun findAllByRoleId(roleId: Long): Flow<MemberRole>

    /**
     * Удаляет сущности связей с ролями у участника с [memberId].
     */
    suspend fun deleteAllByMemberId(memberId: Long)
}