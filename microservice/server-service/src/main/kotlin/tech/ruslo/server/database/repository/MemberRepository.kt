package tech.ruslo.server.database.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.server.database.entity.Member

/**
 * Репозиторий для работы с сущностью участника сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MemberRepository : CoroutineCrudRepository<Member, Long> {
    /**
     * Возвращает сущности участников пользователя с идентификатором [userId] на серверах.
     */
    fun findAllByUserId(userId: Long): Flow<Member>

    /**
     * Удаляет сущность участника пользователя с идентификатором [userId] с сервера с идентификатором [serverId].
     */
    suspend fun deleteByUserIdAndServerId(userId: Long, serverId: Long)
}