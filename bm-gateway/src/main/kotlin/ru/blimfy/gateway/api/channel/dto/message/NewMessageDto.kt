package ru.blimfy.gateway.api.channel.dto.message

import java.util.UUID
import ru.blimfy.channel.db.entity.Message
import ru.blimfy.common.enumeration.MessageTypes
import ru.blimfy.common.enumeration.MessageTypes.DEFAULT

/**
 * DTO с информацией о новом сообщении канала.
 *
 * @property type тип сообщения.
 * @property content содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewMessageDto(val type: MessageTypes = DEFAULT, val content: String? = null)

/**
 * Возвращает сущность сообщения канала из DTO представления.
 */
fun NewMessageDto.toEntity(channelId: UUID, authorId: UUID) = Message(channelId, authorId, type)
    .apply { content = this@toEntity.content }