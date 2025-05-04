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
 * @property username имя пользователя участника сервера.
 * @property serverUserName имя участника сервера для конкретного сервера.
 * @property createdDate дата создания.
 * @property roles роли.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MemberDetailsDto(
    val id: UUID,
    val serverId: UUID,
    val userId: UUID,
    val username: String,
    val serverUserName: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC") val createdDate: Instant,
) {
    lateinit var roles: List<String>
}

/**
 * Возвращает DTO представление сущности участника сервера с подробной информацией.
 */
fun Member.toDetailsDto() =
    MemberDetailsDto(id, serverId, userId, username, serverUsername, createdDate)