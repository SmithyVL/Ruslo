package ru.blimfy.channel.usecase.message

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Message
import ru.blimfy.channel.db.repository.MessageRepository
import ru.blimfy.channel.usecase.exception.ChannelErrors.MESSAGE_BY_ID_NOT_FOUND
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.common.util.DatabaseUtils.getDefaultPageable

/**
 * Реализация интерфейса для работы с сообщениями каналов.
 *
 * @property repo репозиторий для работы с личными сообщениями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MessageServiceImpl(private val repo: MessageRepository) : MessageService {
    override suspend fun createMessage(message: Message) =
        repo.findMaxPositionByChannelId(message.channelId).let { maxPosition ->
            message.position = maxPosition + 1
            repo.save(message)
        }

    override suspend fun setContent(id: UUID, content: String) =
        findMessage(id).apply { this.content = content }.let { repo.save(it) }

    override suspend fun setPinned(id: UUID, pinned: Boolean) {
        findMessage(id).apply { this.pinned = pinned }.let { repo.save(it) }
    }

    override suspend fun findMessage(id: UUID) =
        repo.findById(id) ?: throw NotFoundException(MESSAGE_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findMessages(channelId: UUID, pageNumber: Int, pageSize: Int) =
        repo.findAllByChannelId(channelId, getDefaultPageable(pageNumber, pageSize))

    override suspend fun findPinnedMessages(channelId: UUID, pageNumber: Int, pageSize: Int) =
        repo.findAllByChannelIdAndPinnedIsTrue(channelId, getDefaultPageable(pageNumber, pageSize))

    override suspend fun deleteMessage(id: UUID, authorId: UUID) =
        findMessage(id).let { repo.deleteByIdAndAuthorId(id = id, authorId = authorId) }

    override suspend fun deleteChannelMessages(channelId: UUID) =
        repo.deleteByChannelId(channelId)
}