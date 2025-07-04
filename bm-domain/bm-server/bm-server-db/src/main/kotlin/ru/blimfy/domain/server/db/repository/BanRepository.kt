package ru.blimfy.domain.server.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.domain.server.db.entity.Ban

/**
 * Репозиторий для работы с сущностью бана сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface BanRepository : CoroutineCrudRepository<Ban, UUID> {
    /**
     * Возвращает страницу сущностей банов для сервера с [serverId] с позиции [start] по [end].
     */
    @Query(
        """
            select * from ban 
            where server_id = :serverId and position >= :start and position <= :end
            order by position desc
        """
    )
    fun findPageBans(serverId: UUID, start: Long, end: Long): Flow<Ban>

    /**
     * Возвращает сущности банов для сервера с [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Ban>

    /**
     * Возвращает сущность бана для сервера с [serverId] и пользователя с [userId].
     */
    suspend fun findByServerIdAndUserId(serverId: UUID, userId: UUID): Ban?

    /**
     * Возвращает количество сущностей банов сервера с [serverId].
     */
    suspend fun countByServerId(serverId: UUID): Long

    /**
     * Удаляет сущность бана для сервера с [serverId] и пользователя с [userId].
     */
    suspend fun deleteByServerIdAndUserId(serverId: UUID, userId: UUID)
}