package ru.blimfy.services.channel

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.common.dto.ChannelDto
import ru.blimfy.exception.Errors.CHANNEL_BY_ID_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.Channel
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.persistence.repository.ChannelRepository

/**
 * Реализация интерфейса для работы с каналами категорий.
 *
 * @property channelRepo репозиторий для работы с каналами категорий в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelServiceImpl(private val channelRepo: ChannelRepository) : ChannelService {
    override suspend fun saveChannel(channel: ChannelDto) = channelRepo.save(channel.toEntity()).toDto()

    override suspend fun findChannel(id: UUID) =
        channelRepo.findById(id)
            ?.toDto()
            ?: throw NotFoundException(CHANNEL_BY_ID_NOT_FOUND.msg.format(id))

    override fun findServerChannels(serverId: UUID) =
        channelRepo.findAllByServerId(serverId).map(Channel::toDto)

    override suspend fun deleteChannel(id: UUID) = channelRepo.deleteById(id)
}