package ru.blimfy.gateway.api.mapper

import java.util.UUID
import org.springframework.stereotype.Component
import ru.blimfy.channel.db.entity.Message
import ru.blimfy.channel.usecase.message.MessageService
import ru.blimfy.common.json.MessageReferenceTypes.DEFAULT
import ru.blimfy.common.json.MessageReferenceTypes.FORWARD
import ru.blimfy.common.json.MessageSnapshot
import ru.blimfy.gateway.api.channel.dto.message.MessageDto
import ru.blimfy.gateway.api.channel.dto.message.MessageSnapshotDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.mapper.base.BaseMapper
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения сообщения в DTO.
 *
 * @property messageService сервис для работы с сообщениями.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class MessageMapper(
    private val messageService: MessageService,
    private val userService: UserService,
) : BaseMapper<Message, MessageDto>() {
    override suspend fun toDtoWithRelations(entity: Message) =
        entity.toDto().apply {
            entity.messageReference?.let { messageReference ->
                when (messageReference.type) {
                    DEFAULT -> {
                        referencedMessage = messageService.findMessage(messageReference.messageId).toDto()
                    }

                    FORWARD -> {
                        messageSnapshot = entity.messageSnapshot!!.toSnapshotDto()
                    }
                }
            }
        }

    override suspend fun Message.toDto() =
        MessageDto(id, channelId, type, pinned, content, mentionEveryone, messageReference, createdDate, updatedDate)
            .apply {
                this@apply.author = userService.findUser(this@toDto.authorId).toDto()
                this@apply.mentions = mentionsToUsers(this@toDto.mentions)
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
}