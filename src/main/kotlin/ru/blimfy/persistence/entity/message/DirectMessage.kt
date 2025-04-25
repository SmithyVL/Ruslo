package ru.blimfy.persistence.entity.message

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.direct.message.DirectMessageDto
import ru.blimfy.common.dto.direct.message.NewDirectMessageDto
import ru.blimfy.persistence.entity.base.BaseMessage

/**
 * Сущность с информацией о сообщениях личных диалогов.
 *
 * @param content содержимое сообщения.
 * @property authorId идентификатор автора сообщения.
 * @property conservationId идентификатор личного диалога.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class DirectMessage(content: String, val authorId: UUID, val conservationId: UUID) : BaseMessage(content)

/**
 * Возвращает DTO представления сущности сообщения личного диалога.
 */
fun DirectMessage.toDto() =
    DirectMessageDto(id, authorId, conservationId, content, fileUrl, createdDate, updatedDate)

/**
 * Возвращает сущность сообщения личного диалога из DTO представления нового сообщения личного диалога для [authorId].
 */
fun NewDirectMessageDto.toEntity(authorId: UUID) =
    DirectMessage(content, authorId, conservationId).apply { fileUrl = this@toEntity.fileUrl }

/**
 * Возвращает сущность сообщения личного диалога из DTO представления сообщения личного диалога для [authorId].
 */
fun DirectMessageDto.toEntity(authorId: UUID) =
    DirectMessage(content, authorId, conservationId).apply {
        id = this@toEntity.id
        fileUrl = this@toEntity.fileUrl
        createdDate = this@toEntity.createdDate
        updatedDate = this@toEntity.updatedDate
    }