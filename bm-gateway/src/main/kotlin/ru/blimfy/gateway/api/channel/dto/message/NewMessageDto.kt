package ru.blimfy.gateway.api.channel.dto.message

import java.util.UUID
import ru.blimfy.channel.db.entity.Message
import ru.blimfy.common.enumeration.MessageTypes
import ru.blimfy.common.enumeration.MessageTypes.DEFAULT
import ru.blimfy.common.json.MessageReference

/**
 * DTO с информацией о новом сообщении.
 *
 * @property type тип.
 * @property content содержимое.
 * @property messageReference ссылка на сообщение.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewMessageDto(
    val type: MessageTypes = DEFAULT,
    val content: String? = null,
    var messageReference: MessageReference? = null,
)

/**
 * Возвращает сущность сообщения с [channelId] и [authorId] из DTO.
 */
fun NewMessageDto.toEntity(channelId: UUID, authorId: UUID) = Message(channelId, authorId, type)
    .apply {
        content = this@toEntity.content
        messageReference = this@toEntity.messageReference
    }