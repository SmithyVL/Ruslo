package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.DirectMessageDto
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Модель с информацией о сообщениях личных диалогов.
 *
 * @property authorId идентификатор автора сообщения.
 * @property conservationId идентификатор личного диалога.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class DirectMessage(val authorId: UUID, val conservationId: UUID, val content: String) : WithBaseData {
    var fileUrl: String? = null

    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}

/**
 * Возвращает DTO представления сущности приглашения на сервер.
 */
fun DirectMessage.toDto() = DirectMessageDto(
    id, authorId, conservationId, content, fileUrl, createdDate, updatedDate,
)

/**
 * Возвращает сущность приглашения на сервер из DTO.
 */
fun DirectMessageDto.toEntity() = DirectMessage(authorId, conservationId, content).apply {
    this@toEntity.fileUrl?.let { fileUrl = it }
    this@toEntity.id?.let { id = it }
    this@toEntity.createdDate?.let { createdDate = it }
    this@toEntity.updatedDate?.let { updatedDate = it }
}