package ru.blimfy.domain.channel.usecase.message

import java.util.UUID
import java.util.UUID.fromString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.domain.channel.api.dto.message.NewMessageDto
import ru.blimfy.domain.channel.db.entity.Message
import ru.blimfy.domain.channel.db.entity.toSnapshot
import ru.blimfy.domain.channel.db.repository.MessageRepository
import ru.blimfy.domain.channel.usecase.exception.ChannelErrors.MESSAGE_NOT_FOUND
import ru.blimfy.domain.channel.usecase.exception.ChannelErrors.PINNED_LIMIT
import ru.blimfy.domain.converter.MessageReference
import ru.blimfy.domain.converter.MessageReferenceTypes.FORWARD
import ru.blimfy.domain.converter.MessageReferenceTypes.valueOf as messageReferenceTypesValueOf
import ru.blimfy.domain.converter.enumerations.MessageTypes.valueOf as messageTypesValueOf

/**
 * Реализация интерфейса для работы с сообщениями каналов.
 *
 * @property repo репозиторий для работы с личными сообщениями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MessageServiceImpl(private val repo: MessageRepository) : MessageService {
    override suspend fun createMessage(newMessageDto: NewMessageDto, channelId: UUID, userId: UUID) =
        newMessageDto.toEntity(channelId, userId)
            .apply { position = getCountMessages(channelId) }
            .let { repo.save(it) }
            .apply { fetchReferenced() }

    override suspend fun findMessages(
        channelId: UUID,
        around: UUID?,
        before: UUID?,
        after: UUID?,
        limit: Int,
    ): Flow<Message> {
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

        return repo.findPageMessages(channelId, start, end).onEach { it.fetchReferenced() }
    }

    override suspend fun findPinnedMessages(channelId: UUID) =
        repo.findAllByChannelIdAndPinnedIsTrue(channelId)

    override suspend fun setContent(id: UUID, content: String) =
        modifyMessage(id) {
            it.content = content
            it
        }

    override suspend fun setPinned(id: UUID, pinned: Boolean) {
        modifyMessage(id) {
            if (pinned) {
                require(repo.countByChannelIdAndPinnedIsTrue(it.channelId) >= MAX_PINNED) {
                    PINNED_LIMIT.msg
                }
            }

            it.pinned = pinned
            it
        }
    }

    override suspend fun deleteMessage(id: UUID, authorId: UUID) =
        repo.deleteByIdAndAuthorId(id, authorId)

    /**
     * Возвращает сообщение с [id], обновлённое с использованием [callback].
     */
    private suspend fun modifyMessage(id: UUID, callback: suspend (Message) -> Message) =
        callback(findMessageOrThrow(id))
            .let { repo.save(it) }

    /**
     * Возвращает сообщение с [id] или выбрасывает исключение, если оно не найдено.
     */
    private suspend fun findMessageOrThrow(id: UUID) =
        repo.findById(id) ?: throw NotFoundException(MESSAGE_NOT_FOUND.msg)

    /**
     * Возвращает количество сообщений в канале с [channelId].
     */
    private suspend fun getCountMessages(channelId: UUID) =
        repo.countByChannelId(channelId)

    /**
     * Подгружает связанное сообщение в сообщение.
     */
    private suspend fun Message.fetchReferenced() {
        referencedMessage = messageReference
            ?.messageId
            ?.let { repo.findById(it) }
    }

    /**
     * Возвращает сущность сообщения для канала с [channelId] и пользователя с [userId].
     */
    private suspend fun NewMessageDto.toEntity(channelId: UUID, userId: UUID) =
        Message(channelId, userId, messageTypesValueOf(type)).apply {
            this@toEntity.content?.let {
                content = it
                mentionEveryone = hasMentionEveryone(it)
                mentions = getMentions(it)
            }

            this@toEntity.messageReference?.let {
                messageReference = MessageReference(
                    messageReferenceTypesValueOf(it.type),
                    it.messageId,
                    it.channelId,
                    it.serverId,
                )

                if (messageReferenceTypesValueOf(it.type) == FORWARD) {
                    val snapshotMessage = findMessageOrThrow(it.messageId)
                    messageSnapshot = snapshotMessage.toSnapshot()
                }
            }
        }

    /**
     * Возвращает флаг того, что сообщение в своём [content] упоминает всех.
     */
    private fun hasMentionEveryone(content: String) =
        content.contains(ROLE_DEFAULT_NAME) || content.contains(MENTION_ONLINE_USERS)

    /**
     * Возвращает идентификаторы упоминаний пользователей из [content] сообщения.
     */
    private fun getMentions(content: String): Set<UUID>? {
        val regex = Regex("<@(.+?)>")
        val matches = regex.findAll(content)
        return matches
            .map { it.groupValues[1] }
            .map { fromString(it) }
            .toSet()
    }

    private companion object {
        /**
         * Максимальное количество закреплённых сообщений в канале.
         */
        private const val MAX_PINNED = 25

        /**
         * Упоминание пользователей, находящихся онлайн.
         */
        private const val MENTION_ONLINE_USERS = "@онлайн"

        /**
         * Дефолтное название роли.
         */
        private const val ROLE_DEFAULT_NAME = "@все"
    }
}