package ru.blimfy.persistence.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

/**
 * Репозиторий для работы с сущностью канала сервера в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface ChannelRepository : CoroutineCrudRepository<ru.blimfy.persistence.entity.Channel, UUID> {
    /**
     * Возвращает все сущности каналов для сервера с идентификатором [serverId].
     */
    fun findAllByServerId(serverId: UUID): Flow<ru.blimfy.persistence.entity.Channel>
}