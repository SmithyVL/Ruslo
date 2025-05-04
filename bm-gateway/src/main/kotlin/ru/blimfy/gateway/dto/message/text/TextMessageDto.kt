package ru.blimfy.gateway.dto.message.text

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.server.db.entity.TextMessage

/**
 * DTO с информацией о сообщении канала.
 *
 * @property id идентификатор.
 * @property authorUserId идентификатор пользователя, создавшего сообщение в канале сервера.
 * @property channelId идентификатор канала.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class TextMessageDto(
    val id: UUID,
    val authorUserId: UUID,
    val channelId: UUID,
    val content: String,
    val fileUrl: String? = null,

    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC")
    val createdDate: Instant,

    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC")
    val updatedDate: Instant? = null,
)

/**
 * Возвращает сущность текстового сообщения канала сервера из DTO представления текстового сообщения канала сервера.
 */
fun TextMessageDto.toEntity(authorUserId: UUID) = TextMessage(content, authorUserId, channelId).apply {
    id = this@toEntity.id
    fileUrl = this@toEntity.fileUrl
    createdDate = this@toEntity.createdDate
    updatedDate = this@toEntity.updatedDate
}

/**
 * Возвращает DTO представления сущности текстового сообщения канала сервера.
 */
fun TextMessage.toDto() =
    TextMessageDto(id, authorUserId, channelId, content, fileUrl, createdDate, updatedDate)