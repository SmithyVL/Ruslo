package ru.blimfy.domain.channel.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.domain.channel.db.entity.Invite

/**
 * Репозиторий для работы с сущностью приглашения на каналы в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface InviteRepository : CoroutineCrudRepository<Invite, UUID> {
    /**
     * Возвращает сущности приглашений на канал с [channelId].
     */
    fun findAllByChannelId(channelId: UUID): Flow<Invite>

    /**
     * Возвращает сущности приглашений сервера с [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<Invite>
}