package ru.blimfy.gateway.dto.message

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.common.json.INSTANT_FORMAT
import ru.blimfy.common.json.INSTANT_TIMEZONE
import ru.blimfy.domain.converter.MessageReference
import ru.blimfy.gateway.dto.user.UserDto

/**
 * DTO с информацией о сообщении канала.
 *
 * @property id идентификатор.
 * @property channelId идентификатор канала.
 * @property type тип сообщения.
 * @property pinned является ли сообщение закреплённым.
 * @property content содержимое сообщения.
 * @property mentionEveryone упоминает ли это сообщение всех.
 * @property messageReference ссылка на связанное сообщение.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @property mentions упомянутые пользователи.
 * @property referencedMessage источник ответа сообщения.
 * @property messageSnapshot пересланное сообщение.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessageDto(
    val id: UUID,
    val channelId: UUID,
    val type: String,
    val pinned: Boolean = false,
    val content: String? = null,
    val mentionEveryone: Boolean = false,
    val messageReference: MessageReference? = null,
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val updatedDate: Instant? = null,
) {
    lateinit var author: UserDto
    var mentions: List<UserDto>? = null
    var referencedMessage: MessageDto? = null
    var messageSnapshot: MessageSnapshotDto? = null
}