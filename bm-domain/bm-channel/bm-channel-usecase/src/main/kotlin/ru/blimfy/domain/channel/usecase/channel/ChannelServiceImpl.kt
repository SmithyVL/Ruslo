package ru.blimfy.domain.channel.usecase.channel

import java.util.UUID
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.domain.channel.api.dto.channel.ChannelPositionDto
import ru.blimfy.domain.channel.api.dto.channel.ModifyChannelDto
import ru.blimfy.domain.channel.api.dto.channel.NewChannelDto
import ru.blimfy.domain.channel.db.entity.Channel
import ru.blimfy.domain.channel.db.entity.ChannelTypes.TEXT
import ru.blimfy.domain.channel.db.entity.ChannelTypes.VOICE
import ru.blimfy.domain.channel.db.entity.ChannelTypes.valueOf
import ru.blimfy.domain.channel.db.repository.ChannelRepository
import ru.blimfy.domain.channel.usecase.exception.ChannelErrors.CHANNEL_NOT_FOUND

/**
 * Реализация интерфейса для работы с каналами.
 *
 * @property repo репозиторий для работы с каналами в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelServiceImpl(private val repo: ChannelRepository) : ChannelService {
    override suspend fun createChannel(newChannelDto: NewChannelDto, ownerId: UUID?, serverId: UUID?) =
        newChannelDto.toEntity(ownerId, serverId)
            .apply {
                serverId?.let {
                    position = if (parentId == null) {
                        repo.countByServerIdAndParentIdIsNull(it)
                    } else {
                        repo.countByServerIdAndParentIdIsNotNull(it)
                    }
                }
            }
            .let { repo.save(it) }

    override suspend fun createDefaultChannels(serverId: UUID) {
        Channel(TEXT, serverId)
            .apply { name = SERVER_DEFAULT_TEXT_CHANNEL }
            .let { repo.save(it) }

        Channel(VOICE, serverId)
            .apply { name = SERVER_DEFAULT_VOICE_CHANNEL }
            .let { repo.save(it) }
    }

    override suspend fun findChannel(id: UUID) =
        findChannelOrThrow(id)

    override suspend fun findChannel(recipients: Set<UUID>) =
        repo.findByRecipients(
            recipients.elementAt(0),
            recipients.elementAt(1),
        )

    override fun findDmChannels(userId: UUID) =
        repo.findAllByRecipientId(userId)

    override fun findServerChannels(serverId: UUID) =
        repo.findAllByServerId(serverId)

    override suspend fun modifyChannel(id: UUID, modifyChannelDto: ModifyChannelDto) =
        modifyChannel(id) {
            modifyChannelDto.toEntity(it)
        }

    override suspend fun addRecipients(id: UUID, recipients: Set<UUID>) =
        modifyChannel(id) {
            it.recipients = it.recipients!!.plus(recipients)
            it
        }

    override suspend fun deleteRecipient(id: UUID, recipient: UUID) =
        modifyChannel(id) {
            it.recipients = it.recipients!!.minus(recipient)
            it
        }

    override suspend fun changeOwner(id: UUID, ownerId: UUID) =
        modifyChannel(id) {
            it.ownerId = ownerId
            it
        }

    override fun modifyPositions(positionDtos: List<ChannelPositionDto>, serverId: UUID) =
        positionDtos.asFlow().map { positionDto ->
            modifyChannel(positionDto.id) {
                it.position = positionDto.position
                it.parentId = positionDto.parentId
                it
            }
        }

    override suspend fun deleteChannel(id: UUID) =
        findChannelOrThrow(id).apply { repo.deleteById(id) }

    /**
     * Возвращает канал с [id], обновлённый с использованием [callback].
     */
    private suspend fun modifyChannel(id: UUID, callback: (Channel) -> Channel) =
        callback(findChannel(id))
            .let { repo.save(it) }

    /**
     * Возвращает канал с [id] или выбрасывает исключение, если он не найден.
     */
    private suspend fun findChannelOrThrow(id: UUID) =
        repo.findById(id) ?: throw NotFoundException(CHANNEL_NOT_FOUND.msg)

    private companion object {
        /**
         * Стандартное название текстового канала сервера.
         */
        private const val SERVER_DEFAULT_TEXT_CHANNEL = "Текстовый канал"

        /**
         * Стандартное название голосового канала сервера.
         */
        private const val SERVER_DEFAULT_VOICE_CHANNEL = "Голосовой канал"
    }
}

/**
 * Возвращает сущность канала для сервера с [serverId] (если он не указан, то создаётся личный канал) и
 * пользователя с [ownerId] (если он не указан, то создаётся канал сервера) из DTO представления нового канала.
 */
fun NewChannelDto.toEntity(ownerId: UUID? = null, serverId: UUID? = null) =
    Channel(valueOf(type), serverId).apply {
        this.ownerId = ownerId
        recipients = this@toEntity.recipients
        name = this@toEntity.name
        parentId = this@toEntity.parentId
    }

/**
 * Возвращает сущность из DTO представления обновлённого [channel].
 */
fun ModifyChannelDto.toEntity(channel: Channel) =
    Channel(channel.type, channel.serverId).apply {
        nsfw = this@toEntity.nsfw
        topic = this@toEntity.topic
        name = this@toEntity.name
        icon = this@toEntity.icon
        id = channel.id
        parentId = channel.parentId
        position = channel.position
        ownerId = channel.ownerId
        recipients = channel.recipients
    }