package tech.ruslo.channel.dto.message

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import tech.ruslo.date.format.INSTANT_FORMAT
import tech.ruslo.date.format.INSTANT_TIMEZONE
import tech.ruslo.channel.dto.message.enumerations.MessageTypes
import tech.ruslo.channel.dto.message.enumerations.MessageTypes.DEFAULT

/**
 * DTO с информацией о сообщении канала.
 *
 * @property id идентификатор.
 * @property channelId идентификатор канала.
 * @property authorId идентификатор автора сообщения.
 * @property type тип сообщения.
 * @property content содержимое сообщения.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessageDto(
    val id: Long? = null,
    val channelId: Long,
    val authorId: Long,
    val type: MessageTypes = DEFAULT,
    val content: String,
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val updatedDate: Instant? = null,
)