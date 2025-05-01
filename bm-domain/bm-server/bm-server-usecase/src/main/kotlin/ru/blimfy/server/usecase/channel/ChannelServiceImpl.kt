package ru.blimfy.server.usecase.channel

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.server.db.entity.Channel
import ru.blimfy.server.db.repository.ChannelRepository
import ru.blimfy.server.usecase.exception.ServerErrors.CHANNEL_BY_ID_NOT_FOUND

/**
 * Реализация интерфейса для работы с каналами категорий.
 *
 * @property channelRepo репозиторий для работы с каналами категорий в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelServiceImpl(private val channelRepo: ChannelRepository) : ChannelService {
    override suspend fun saveChannel(channel: Channel) = channelRepo.save(channel)

    override suspend fun findChannel(id: UUID) = channelRepo.findById(id)
        ?: throw NotFoundException(CHANNEL_BY_ID_NOT_FOUND.msg.format(id))

    override fun findServerChannels(serverId: UUID) =
        channelRepo.findAllByServerId(serverId)

    override suspend fun deleteChannel(id: UUID) = channelRepo.deleteById(id)
}