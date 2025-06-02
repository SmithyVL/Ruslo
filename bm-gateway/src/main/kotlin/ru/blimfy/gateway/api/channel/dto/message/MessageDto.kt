package ru.blimfy.gateway.api.channel.dto.message

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.Instant
import java.util.UUID
import ru.blimfy.channel.db.entity.Message
import ru.blimfy.common.enumeration.MessageTypes
import ru.blimfy.common.enumeration.MessageTypes.DEFAULT
import ru.blimfy.gateway.api.dto.UserDto
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE

/**
 * DTO с информацией о сообщении канала.
 *
 * @property id идентификатор.
 * @property channelId идентификатор канала.
 * @property type тип сообщения.
 * @property content содержимое сообщения.
 * @property mentionEveryone упоминает ли это сообщение всех.
 * @property pinned является ли сообщение закреплённым.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @property mentions упомянутые пользователи.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class MessageDto(
    val id: UUID,
    val channelId: UUID,
    val type: MessageTypes = DEFAULT,
    val content: String? = null,
    val mentionEveryone: Boolean = false,
    val pinned: Boolean = false,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val updatedDate: Instant? = null,
) {
    lateinit var author: UserDto
    var mentions: List<UserDto>? = null
}

/**
 * Возвращает DTO представления сущности сообщения канала.
 */
fun Message.toDto() = MessageDto(
    id,
    channelId,
    type,
    content,
    mentionEveryone,
    pinned,
    createdDate,
    updatedDate,
)