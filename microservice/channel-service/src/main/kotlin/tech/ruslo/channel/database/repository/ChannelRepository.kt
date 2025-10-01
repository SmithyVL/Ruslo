package tech.ruslo.channel.database.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import tech.ruslo.channel.database.entity.Channel

/**
 * Репозиторий для работы с сущностью личного канала в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface ChannelRepository : CoroutineCrudRepository<Channel, Long> {
    /**
     * Возвращает количество сущностей каналов на уровне сервера с [serverId].
     */
    suspend fun countByServerIdAndParentIdIsNull(serverId: Long): Long

    /**
     * Возвращает количество сущностей каналов, которые расположены в категориях сервера с [serverId].
     */
    suspend fun countByServerIdAndParentIdIsNotNull(serverId: Long): Long
}