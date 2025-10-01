package tech.ruslo.channel.mapper

import tech.ruslo.channel.database.entity.Message
import tech.ruslo.channel.dto.message.MessageDto
import tech.ruslo.channel.dto.message.enumerations.MessageTypes.valueOf

/**
 * Возвращает сущность канала из DTO представления сообщения.
 */
fun MessageDto.toEntity() = Message(channelId, authorId, type.name).apply {
    this@toEntity.id?.let { id = it }
    content = this@toEntity.content
    createdDate = this@toEntity.createdDate
    updatedDate = this@toEntity.updatedDate
}

/**
 * Возвращает DTO представление сообщения с информацией из сущности сообщения.
 */
fun Message.toDto() =
    MessageDto(id, channelId, authorId, valueOf(type), content, createdDate, updatedDate)