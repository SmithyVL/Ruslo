package tech.ruslo.channel.database.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.channel.database.entity.Message

/**
 * Репозиторий для работы с сущностью сообщения канала в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MessageRepository : CoroutineCrudRepository<Message, Long> {
    /**
     * Возвращает страницу сущностей сообщений для канала с [channelId] с позиции [start] по [end].
     */
    @Query(
        """
            select * from message 
            where channel_id = :channelId and position >= :start and position <= :end
            order by position desc
        """
    )
    fun findPageMessages(channelId: Long, start: Long, end: Long): Flow<Message>

    /**
     * Возвращает количество сообщений в канале с [channelId]
     */
    suspend fun countByChannelId(channelId: Long): Long
}