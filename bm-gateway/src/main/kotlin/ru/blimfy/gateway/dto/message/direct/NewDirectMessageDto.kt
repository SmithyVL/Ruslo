package ru.blimfy.gateway.dto.message.direct

import java.util.UUID
import ru.blimfy.direct.db.entity.DirectMessage

/**
 * DTO с информацией о новом личном сообщении.
 *
 * @property conservationId идентификатор личного диалога.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewDirectMessageDto(val conservationId: UUID, val content: String, val fileUrl: String? = null)

/**
 * Возвращает сущность сообщения личного диалога из DTO представления нового сообщения личного диалога для [authorId].
 */
fun NewDirectMessageDto.toEntity(authorId: UUID) =
    DirectMessage(conservationId, authorId, content).apply { fileUrl = this@toEntity.fileUrl }