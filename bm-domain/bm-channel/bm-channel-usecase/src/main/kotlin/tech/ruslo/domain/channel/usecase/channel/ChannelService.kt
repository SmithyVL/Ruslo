package tech.ruslo.domain.channel.usecase.channel

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import tech.ruslo.domain.channel.api.dto.channel.ChannelPositionDto
import tech.ruslo.domain.channel.api.dto.channel.ModifyChannelDto
import tech.ruslo.domain.channel.api.dto.channel.NewChannelDto
import tech.ruslo.domain.channel.db.entity.Channel

/**
 * Интерфейс для работы с каналами.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ChannelService {
    /**
     * Возвращает канал, созданный по информации из [newChannelDto]. Если указан [ownerId], то создаётся группа, а
     * если [serverId] - канал сервера.
     */
    suspend fun createChannel(newChannelDto: NewChannelDto, ownerId: UUID? = null, serverId: UUID? = null): Channel

    /**
     * Создаёт дефолтные каналы для сервера с [serverId].
     */
    suspend fun createDefaultChannels(serverId: UUID)

    /**
     * Возвращает канал с [id].
     */
    suspend fun findChannel(id: UUID): Channel

    /**
     * Возвращает личный канал для [recipients], если такой есть.
     */
    suspend fun findChannel(recipients: Set<UUID>): Channel?

    /**
     * Возвращает личные каналы для [userId].
     */
    fun findDmChannels(userId: UUID): Flow<Channel>

    /**
     * Возвращает каналы сервера с [serverId].
     */
    fun findServerChannels(serverId: UUID): Flow<Channel>

    /**
     * Возвращает канал с [id], обновлённый по информации из [modifyChannelDto].
     */
    suspend fun modifyChannel(id: UUID, modifyChannelDto: ModifyChannelDto): Channel

    /**
     * Возвращает канал с [id] и новыми [recipients].
     */
    suspend fun addRecipients(id: UUID, recipients: Set<UUID>): Channel

    /**
     * Возвращает канал с [id] и удалённым [recipient].
     */
    suspend fun deleteRecipient(id: UUID, recipient: UUID): Channel

    /**
     * Возвращает группу с [id] и новым [ownerId].
     */
    suspend fun changeOwner(id: UUID, ownerId: UUID): Channel

    /**
     * Возвращает каналы, с обновлённой информацией о [positionDtos], для сервера с [serverId].
     */
    fun modifyPositions(positionDtos: List<ChannelPositionDto>, serverId: UUID): Flow<Channel>

    /**
     * Возвращает удалённый канал с [id].
     */
    suspend fun deleteChannel(id: UUID): Channel
}