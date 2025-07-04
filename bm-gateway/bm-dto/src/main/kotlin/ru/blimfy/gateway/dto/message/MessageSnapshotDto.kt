package ru.blimfy.gateway.dto.message

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import ru.blimfy.common.json.INSTANT_FORMAT
import ru.blimfy.common.json.INSTANT_TIMEZONE
import ru.blimfy.domain.converter.enumerations.MessageTypes
import ru.blimfy.domain.converter.enumerations.MessageTypes.DEFAULT
import ru.blimfy.gateway.dto.user.UserDto

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
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val updatedDate: Instant? = null,
) {
    lateinit var author: UserDto
    var mentions: List<UserDto>? = null
}