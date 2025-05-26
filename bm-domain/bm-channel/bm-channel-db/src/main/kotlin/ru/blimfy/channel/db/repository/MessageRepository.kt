package ru.blimfy.channel.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
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
     * Возвращает страницу сущностей сообщений для канала с [channelId] по конфигурации [pageable] постраничного поиска.
     */
    fun findAllByChannelId(channelId: UUID, pageable: Pageable): Flow<Message>

    /**
     * Возвращает страницу закреплённых сущностей сообщений для канала с [channelId] по конфигурации [pageable]
     * постраничного поиска.
     */
    fun findAllByChannelIdAndPinnedIsTrue(channelId: UUID, pageable: Pageable): Flow<Message>

    /**
     * Удаляет сущность сообщения с [id] от пользователя с идентификатором [authorId].
     */
    suspend fun deleteByIdAndAuthorId(id: UUID, authorId: UUID)

    /**
     * Удаляет сущности сообщений канала с [channelId].
     */
    suspend fun deleteByChannelId(channelId: UUID)

    /**
     * Возвращает максимальную позицию сообщения в канале с [channelId]
     */
    @Query("select max(position) from message where channel_id = :channelId")
    suspend fun findMaxPositionByChannelId(channelId: UUID): Long
}