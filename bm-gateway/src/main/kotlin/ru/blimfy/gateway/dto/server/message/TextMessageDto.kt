package ru.blimfy.gateway.dto.server.message

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
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
 * @property pinned является ли сообщение закреплённым.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class TextMessageDto(
    val id: UUID,
    val channelId: UUID,
    val content: String? = null,
    val pinned: Boolean,
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
    pinned = this@toEntity.pinned
    createdDate = this@toEntity.createdDate
    updatedDate = this@toEntity.updatedDate
}

/**
 * Возвращает DTO представления сущности текстового сообщения канала сервера.
 */
fun TextMessage.toDto() = TextMessageDto(id, channelId, content, pinned, createdDate, updatedDate)