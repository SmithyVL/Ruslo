package ru.blimfy.gateway.dto.channel.message

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.Instant
import java.util.UUID
import ru.blimfy.channel.db.entity.Message
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.gateway.dto.user.UserDto

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
@JsonInclude(NON_NULL)
data class MessageDto(
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
 * Возвращает DTO представления сущности сообщения канала.
 */
fun Message.toDto() = MessageDto(id, channelId, content, pinned, createdDate, updatedDate)