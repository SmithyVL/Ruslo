package ru.blimfy.gateway.dto.member

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.server.db.entity.Member

/**
 * DTO с информацией об участнике сервера.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property nick имя пользователя участника сервера.
 * @property joiningDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MemberDto(
    val id: UUID,
    val serverId: UUID,
    val userId: UUID,
    val nick: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC") val joiningDate: Instant,
)

/**
 * Возвращает DTO представления с информацией об участнике сервера.
 */
fun Member.toDto() = MemberDto(id, serverId, userId, nick, joiningDate)