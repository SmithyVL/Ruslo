package ru.blimfy.server.usecase.message

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.common.util.DatabaseUtils.getDefaultPageable
import ru.blimfy.server.db.entity.TextMessage
import ru.blimfy.server.db.repository.TextMessageRepository
import ru.blimfy.server.usecase.exception.ServerErrors.TEXT_MESSAGE_BY_ID_NOT_FOUND

/**
 * Реализация интерфейса для работы с сообщениями каналов.
 *
 * @property messageRepo репозиторий для работы с сообщениями каналов в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class TextMessageServiceImpl(private val messageRepo: TextMessageRepository) : TextMessageService {
    override suspend fun createMessage(message: TextMessage) = messageRepo.save(message)

    override suspend fun setContent(id: UUID, newContent: String) =
        findMessage(id).apply { content = newContent }.let { messageRepo.save(it) }

    override suspend fun setPinned(id: UUID, newPinned: Boolean) =
        findMessage(id).apply { pinned = newPinned }.let { messageRepo.save(it) }

    override suspend fun findMessage(id: UUID) = messageRepo.findById(id)
        ?: throw NotFoundException(TEXT_MESSAGE_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findPageChannelMessages(channelId: UUID, pageNumber: Int, pageSize: Int) =
        messageRepo.findAllByChannelId(channelId, getDefaultPageable(pageNumber, pageSize))

    override suspend fun deleteMessage(textMessageId: UUID, authorId: UUID) =
        messageRepo.deleteByIdAndAuthorUserId(textMessageId = textMessageId, authorId = authorId)
}