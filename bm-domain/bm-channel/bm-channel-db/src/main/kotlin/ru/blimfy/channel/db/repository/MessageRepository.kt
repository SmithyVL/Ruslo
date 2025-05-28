package ru.blimfy.channel.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.channel.db.entity.Message

/**
 * Репозиторий для работы с сущностью сообщения личного канала в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MessageRepository : CoroutineCrudRepository<Message, UUID> {
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
    fun findPageMessages(channelId: UUID, start: Long, end: Long): Flow<Message>

    /**
     * Возвращает сущности закреплённых сообщений для канала с [channelId].
     */
    fun findAllByChannelIdAndPinnedIsTrue(channelId: UUID): Flow<Message>

    /**
     * Удаляет сущность сообщения с [id] от пользователя с идентификатором [authorId].
     */
    suspend fun deleteByIdAndAuthorId(id: UUID, authorId: UUID)

    /**
     * Удаляет сущности сообщений канала с [channelId].
     */
    suspend fun deleteByChannelId(channelId: UUID)

    /**
     * Возвращает количество сообщений в канале с [channelId]
     */
    suspend fun countByChannelId(channelId: UUID): Long

    /**
     * Возвращает количество сущностей сообщений, которые закреплены в канале с [channelId]
     */
    suspend fun countByChannelIdAndPinnedIsTrue(channelId: UUID): Long
}