package ru.blimfy.services.message

import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.blimfy.persistence.entity.TextMessage
import ru.blimfy.persistence.repository.TextMessageRepository

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

    override suspend fun findPageChannelMessages(channelId: UUID, pageable: Pageable) =
        messageRepo.findAllByChannelId(channelId, pageable)

    override suspend fun deleteMessage(id: UUID) = messageRepo.deleteById(id)
}