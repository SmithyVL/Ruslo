package ru.blimfy.server.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.server.db.entity.Member

/**
 * Репозиторий для работы с сущностью участника сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MemberRepository : CoroutineCrudRepository<Member, UUID> {
    /**
     * Возвращает все сущности участников серверов для пользователя с идентификатором [userId].
     */
    fun findAllByUserId(userId: UUID): Flow<Member>

    /**
     * Возвращает все сущности участников для сервера с идентификатором [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Member>

    /**
     * Возвращает количество сущностей участников сервера с идентификатором [serverId].
     */
    suspend fun countByServerId(serverId: UUID): Long

    /**
     * Возвращает все сущности участников для пользователя с идентификатором [userId] и сервера с идентификатором
     * [serverId].
     */
    suspend fun findByUserIdAndServerId(userId: UUID, serverId: UUID): Member?

    /**
     * Удаляет сущность участника с идентификатором [id] с сервера с идентификатором [serverId].
     */
    suspend fun deleteByIdAndServerId(id: UUID, serverId: UUID)

    /**
     * Удаляет сущность участника пользователя с идентификатором [userId] с сервера с идентификатором [serverId].
     */
    suspend fun deleteByUserIdAndServerId(userId: UUID, serverId: UUID)

    /**
     * Удаляет все сущности участников для сервера с идентификатором [serverId].
     */
    suspend fun deleteAllByServerId(serverId: UUID)
}