package ru.blimfy.persistence.entity.message

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.message.text.NewTextMessageDto
import ru.blimfy.common.dto.message.text.TextMessageDto
import ru.blimfy.persistence.entity.base.BaseMessage

/**
 * Сущность с информацией о сообщениях текстовых каналов.
 *
 * @param content содержимое сообщения.
 * @property authorId идентификатор автора сообщения.
 * @property channelId идентификатор текстового канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class TextMessage(content: String, val authorId: UUID, val channelId: UUID) : BaseMessage(content)

/**
 * Возвращает DTO представления сущности текстового сообщения канала сервера.
 */
fun TextMessage.toDto() =
    TextMessageDto(id, authorId, channelId, content, fileUrl, createdDate, updatedDate)

/**
 * Возвращает сущность текстового сообщения канала сервера из DTO представления текстового сообщения канала сервера.
 */
fun NewTextMessageDto.toEntity(authorId: UUID) =
    TextMessage(content, authorId, channelId).apply { fileUrl = this@toEntity.fileUrl }

/**
 * Возвращает сущность текстового сообщения канала сервера из DTO представления текстового сообщения канала сервера.
 */
fun TextMessageDto.toEntity(authorId: UUID) = TextMessage(content, authorId, channelId).apply {
    id = this@toEntity.id
    fileUrl = this@toEntity.fileUrl
    createdDate = this@toEntity.createdDate
    updatedDate = this@toEntity.updatedDate
}