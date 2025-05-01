package ru.blimfy.gateway.dto.direct.message

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.direct.db.entity.DirectMessage
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT

/**
 * DTO с информацией о личном сообщении.
 *
 * @property id идентификатор.
 * @property authorId идентификатор автора сообщения.
 * @property conservationId идентификатор личного диалога.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class DirectMessageDto(
    val id: UUID,
    val authorId: UUID,
    val conservationId: UUID,
    val content: String,
    val fileUrl: String? = null,

    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC")
    val createdDate: Instant,

    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC")
    val updatedDate: Instant? = null,
)

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

/**
 * Возвращает DTO представления сущности сообщения личного диалога.
 */
fun DirectMessage.toDto() =
    DirectMessageDto(id, authorId, conservationId, content, fileUrl, createdDate, updatedDate)