package ru.blimfy.domain.channel.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.domain.channel.db.entity.Channel

/**
 * Репозиторий для работы с сущностью личного канала в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface ChannelRepository : CoroutineCrudRepository<Channel, UUID> {
    /**
     * Возвращает сущности каналов сервера с [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Channel>

    /**
     * Возвращает сущности каналов для [userId].
     */
    @Query("select * from channel where :recipientId = any(recipients)")
    fun findAllByRecipientId(userId: UUID): Flow<Channel>

    /**
     * Возвращает сущность канала для [recipientIdOne] и [recipientIdTwo].
     */
    @Query("select * from channel where :recipientIdOne = any(recipients) and :recipientIdTwo = any(recipients)")
    suspend fun findByRecipients(recipientIdOne: UUID, recipientIdTwo: UUID): Channel

    /**
     * Возвращает количество сущностей каналов на уровне сервера с [serverId].
     */
    suspend fun countByServerIdAndParentIdIsNull(serverId: UUID): Long

    /**
     * Возвращает количество сущностей каналов, которые расположены в категориях сервера с [serverId].
     */
    suspend fun countByServerIdAndParentIdIsNotNull(serverId: UUID): Long
}