package ru.blimfy.gateway.api.channel.dto.message

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import ru.blimfy.common.enumeration.MessageTypes
import ru.blimfy.common.enumeration.MessageTypes.DEFAULT
import ru.blimfy.gateway.api.dto.UserDto
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE

/**
 * DTO с информацией о пересланном сообщении.
 *
 * @property type тип сообщения.
 * @property content содержимое сообщения.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @property mentions упомянутые пользователи.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessageSnapshotDto(
    val type: MessageTypes = DEFAULT,
    val content: String,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val updatedDate: Instant? = null,
) {
    lateinit var author: UserDto
    var mentions: List<UserDto>? = null
}