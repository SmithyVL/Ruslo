package tech.ruslo.channel.service.channel

import org.springframework.stereotype.Service
import tech.ruslo.channel.database.entity.Channel
import tech.ruslo.channel.database.repository.ChannelRepository

/**
 * Реализация интерфейса для работы с каналами.
 *
 * @property channelRepository репозиторий для работы с каналами в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelServiceImpl(private val channelRepository: ChannelRepository) : ChannelService {
    override suspend fun saveChannel(channel: Channel) = channel
        .apply {
            position = if (parentId == null) {
                channelRepository.countByServerIdAndParentIdIsNull(serverId)
            } else {
                channelRepository.countByServerIdAndParentIdIsNotNull(serverId)
            }
        }
        .let { channelRepository.save(it) }

    override fun saveChannels(channels: List<Channel>) =
        channelRepository.saveAll(channels)

    override suspend fun deleteChannel(id: Long) =
        channelRepository.deleteById(id)
}