package ru.blimfy.server.usecase.message

import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
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
    override suspend fun saveMessage(message: TextMessage) = messageRepo.save(message)

    override suspend fun findMessage(id: UUID) = messageRepo.findById(id)
        ?: throw NotFoundException(TEXT_MESSAGE_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findPageChannelMessages(channelId: UUID, pageable: Pageable) =
        messageRepo.findAllByChannelId(channelId, pageable)

    override suspend fun deleteMessage(textMessageId: UUID, authorId: UUID) =
        messageRepo.deleteByIdAndAuthorUserId(textMessageId = textMessageId, authorId = authorId)
}