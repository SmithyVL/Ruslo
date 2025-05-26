package ru.blimfy.channel.usecase.channel

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.db.repository.ChannelRepository
import ru.blimfy.channel.usecase.exception.ChannelErrors.CHANNEL_BY_ID_NOT_FOUND
import ru.blimfy.channel.usecase.exception.ChannelErrors.DM_CHANNEL_VIEW_ACCESS_DENIED
import ru.blimfy.channel.usecase.exception.ChannelErrors.GROUP_DM_MODIFY_ACCESS_DENIED
import ru.blimfy.channel.usecase.message.MessageService
import ru.blimfy.common.exception.AccessDeniedException
import ru.blimfy.common.exception.NotFoundException

/**
 * Реализация интерфейса для работы с каналами.
 *
 * @property repo репозиторий для работы с каналами в БД.
 * @property messageService сервис для работы с сообщениями канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelServiceImpl(
    private val repo: ChannelRepository,
    private val messageService: MessageService,
) : ChannelService {
    override suspend fun save(channel: Channel): Channel {
        channel.serverId?.let { channel.position = repo.findMaxPositionByServerId(it) + 1 }

        return repo.save(channel)
    }

    override suspend fun addRecipients(id: UUID, recipients: Set<UUID>) =
        findChannel(id).apply { this.recipients = this.recipients!!.plus(recipients) }.let { repo.save(it) }

    override suspend fun deleteRecipient(id: UUID, recipient: UUID) =
        findChannel(id).apply { recipients = recipients!!.minus(recipient) }.let { repo.save(it) }

    override suspend fun changeOwner(id: UUID, newOwnerId: UUID) =
        findChannel(id).apply { ownerId = newOwnerId }.let { repo.save(it) }

    override suspend fun findChannel(id: UUID) =
        repo.findById(id) ?: throw NotFoundException(CHANNEL_BY_ID_NOT_FOUND.msg.format(id))

    override fun findDmChannels(userId: UUID) =
        repo.findAllByRecipientId(userId)

    override fun findServerChannels(serverId: UUID) = repo.findAllByServerId(serverId)

    override suspend fun deleteChannel(id: UUID) =
        findChannel(id).also {
            messageService.deleteChannelMessages(id)
            repo.delete(it)
        }

    override suspend fun findDm(recipients: Set<UUID>) =
        repo.findByRecipients(recipients.elementAt(0), recipients.elementAt(1))

    override suspend fun checkChannelViewAccess(id: UUID, userId: UUID) {
        if (findChannel(id).recipients!!.contains(userId)) {
            throw AccessDeniedException(DM_CHANNEL_VIEW_ACCESS_DENIED.msg.format(id))
        }
    }

    override suspend fun checkGroupDmWriteAccess(id: UUID, userId: UUID) {
        if (userId != findChannel(id).ownerId) {
            throw AccessDeniedException(GROUP_DM_MODIFY_ACCESS_DENIED.msg.format(id))
        }
    }
}