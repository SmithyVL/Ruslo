package ru.blimfy.gateway.api.mapper

import java.util.UUID
import java.util.UUID.fromString
import org.springframework.stereotype.Component
import ru.blimfy.channel.db.entity.Message
import ru.blimfy.channel.db.entity.toSnapshot
import ru.blimfy.channel.usecase.message.MessageService
import ru.blimfy.common.json.MessageReferenceTypes.DEFAULT
import ru.blimfy.common.json.MessageReferenceTypes.FORWARD
import ru.blimfy.common.json.MessageSnapshot
import ru.blimfy.gateway.api.channel.dto.message.MessageDto
import ru.blimfy.gateway.api.channel.dto.message.MessageSnapshotDto
import ru.blimfy.gateway.api.channel.dto.message.NewMessageDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.server.usecase.role.RoleServiceImpl.Companion.DEFAULT_ROLE_NAME
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения сообщения в DTO и обратно.
 *
 * @property messageService сервис для работы с сообщениями.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class MessageMapper(private val messageService: MessageService, private val userService: UserService) {
    /**
     * Возвращает сущность сообщения из [dto] для канала с [channelId] и пользователя с [authorId].
     */
    suspend fun toEntity(dto: NewMessageDto, channelId: UUID, authorId: UUID) =
        Message(channelId, authorId, dto.type).apply {
            dto.content?.let {
                content = dto.content
                mentionEveryone = hasMentionEveryone(it)
                mentions = getMentions(it)
            }

            dto.messageReference?.let {
                messageReference = dto.messageReference

                if (it.type == FORWARD) {
                    val snapshotMessage = messageService.findMessage(messageReference!!.messageId)
                    messageSnapshot = snapshotMessage.toSnapshot()
                }
            }
        }

    /**
     * Возвращает DTO представление [message] со связанной информацией.
     */
    suspend fun toDto(message: Message) =
        message.toBasicDto().apply {
            message.messageReference?.let { messageReference ->
                when (messageReference.type) {
                    DEFAULT -> {
                        referencedMessage = messageService.findMessage(messageReference.messageId).toBasicDto()
                    }

                    FORWARD -> {
                        messageSnapshot = message.messageSnapshot!!.toSnapshotDto()
                    }
                }
            }
        }

    /**
     * Возвращает DTO представление сообщения с базовой информацией.
     */
    private suspend fun Message.toBasicDto() =
        MessageDto(id, channelId, type, pinned, content, mentionEveryone, messageReference, createdDate, updatedDate)
            .apply {
                this@apply.author = userService.findUser(this@toBasicDto.authorId).toDto()
                this@apply.mentions = mentionsToUsers(this@toBasicDto.mentions)
            }

    /**
     * Возвращает флаг того, что сообщение в своём [content] упоминает всех.
     */
    private fun hasMentionEveryone(content: String) =
        content.contains(DEFAULT_ROLE_NAME) || content.contains(MENTION_ONLINE_USERS)

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

    /**
     * Возвращает DTO представление снимка сообщения.
     */
    private suspend fun MessageSnapshot.toSnapshotDto() =
        MessageSnapshotDto(type, content, createdDate, updatedDate).apply {
            this@apply.author = userService.findUser(this@toSnapshotDto.authorId).toDto()
            this@apply.mentions = mentionsToUsers(this@toSnapshotDto.mentions)
        }

    /**
     * Возвращает список пользователей из [mentions].
     */
    private suspend fun mentionsToUsers(mentions: Set<UUID>?) =
        mentions
            ?.map { userService.findUser(it) }
            ?.map(User::toDto)

    private companion object {
        /**
         * Упоминание пользователей, находящихся онлайн.
         */
        private const val MENTION_ONLINE_USERS = "@онлайн"
    }
}