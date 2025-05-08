package ru.blimfy.server.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.server.db.entity.Channel

/**
 * Репозиторий для работы с сущностью канала сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface ChannelRepository : CoroutineCrudRepository<Channel, UUID> {
    /**
     * Возвращает все сущности каналов для сервера с идентификатором [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Channel>

    /**
     * Удаляет сущность канала с идентификатором [channelId] для сервера с идентификатором [serverId].
     */
    suspend fun deleteByIdAndServerId(channelId: UUID, serverId: UUID)
}