package ru.blimfy.gateway.dto.dm.message

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.direct.db.entity.DmMessage
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.gateway.dto.user.UserDto

/**
 * DTO с информацией о личном сообщении.
 *
 * @property id идентификатор.
 * @property dmChannelId идентификатор личного диалога.
 * @property content содержимое сообщения.
 * @property pinned является ли сообщение закреплённым.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class DmMessageDto(
    val id: UUID,
    val dmChannelId: UUID,
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
 * Возвращает DTO представления сущности сообщения личного диалога.
 */
fun DmMessage.toDto() = DmMessageDto(id, dmChannelId, content, pinned, createdDate, updatedDate)