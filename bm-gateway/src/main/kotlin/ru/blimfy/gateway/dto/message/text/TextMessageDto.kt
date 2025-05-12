package ru.blimfy.gateway.dto.message.text

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.common.enumeration.TextMessageTypes
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.server.db.entity.TextMessage

/**
 * DTO с информацией о сообщении канала.
 *
 * @property id идентификатор.
 * @property channelId идентификатор канала.
 * @property content содержимое сообщения.
 * @property type тип сообщения.
 * @property pinned является ли сообщение закреплённым.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class TextMessageDto(
    val id: UUID,
    val channelId: UUID,
    val content: String,
    val type: TextMessageTypes,
    val pinned: Boolean,
    val fileUrl: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val updatedDate: Instant? = null,
) {
    lateinit var author: UserDto
}

/**
 * Возвращает сущность текстового сообщения канала сервера из DTO представления текстового сообщения канала сервера.
 */
fun TextMessageDto.toEntity(authorUserId: UUID) = TextMessage(channelId, authorUserId, content).apply {
    id = this@toEntity.id
    type = this@toEntity.type
    pinned = this@toEntity.pinned
    fileUrl = this@toEntity.fileUrl
    createdDate = this@toEntity.createdDate
    updatedDate = this@toEntity.updatedDate
}

/**
 * Возвращает DTO представления сущности текстового сообщения канала сервера.
 */
fun TextMessage.toDto() =
    TextMessageDto(id, channelId, content, type, pinned, fileUrl, createdDate, updatedDate)