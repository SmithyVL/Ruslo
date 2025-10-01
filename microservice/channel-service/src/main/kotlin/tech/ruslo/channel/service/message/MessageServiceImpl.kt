package tech.ruslo.channel.service.message

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service
import tech.ruslo.channel.database.entity.Message
import tech.ruslo.channel.database.repository.MessageRepository
import tech.ruslo.channel.exception.ChannelErrors.MESSAGE_NOT_FOUND
import tech.ruslo.exceptions.core.NotFoundException

/**
 * Реализация интерфейса для работы с сообщениями каналов.
 *
 * @property messageRepository репозиторий для работы с личными сообщениями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MessageServiceImpl(private val messageRepository: MessageRepository) : MessageService {
    override suspend fun saveMessage(message: Message) = message
        .apply { position = getCountMessages(channelId) }
        .let { messageRepository.save(it) }

    override fun findMessages(
        channelId: Long,
        around: Long?,
        before: Long?,
        after: Long?,
        limit: Int,
    ): Flow<Message> = flow {
        var end: Long
        var start: Long

        when {
            before != null -> {
                end = findMessageOrThrow(before).position
                start = end - limit
            }

            after != null -> {
                start = findMessageOrThrow(after).position
                end = start + limit
            }

            around != null -> {
                val position = findMessageOrThrow(around).position
                start = position - limit
                end = position + limit
            }

            else -> {
                end = getCountMessages(channelId)
                start = end - limit
            }
        }

        messageRepository.findPageMessages(channelId, start, end).collect { emit(it) }
    }

    override suspend fun deleteMessage(id: Long) =
        messageRepository.deleteById(id)

    /**
     * Возвращает сообщение с [id] или выбрасывает исключение, если оно не найдено.
     */
    private suspend fun findMessageOrThrow(id: Long) =
        messageRepository.findById(id) ?: throw NotFoundException(MESSAGE_NOT_FOUND.msg)

    /**
     * Возвращает количество сообщений в канале с [channelId].
     */
    private suspend fun getCountMessages(channelId: Long) =
        messageRepository.countByChannelId(channelId)
}