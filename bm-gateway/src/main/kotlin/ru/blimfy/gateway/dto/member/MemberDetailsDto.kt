package ru.blimfy.gateway.dto.member

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.gateway.dto.role.RoleShortDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.server.db.entity.Member

/**
 * DTO с подробной информацией об участнике сервера.
 *
 * @property id идентификатор участника.
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property nick имя пользователя участника сервера.
 * @property joiningDate дата присоединения к серверу.
 * @property roles роли участника.
 * @property user информация об участнике сервера как пользователе.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MemberDetailsDto(
    val id: UUID,
    val serverId: UUID,
    val userId: UUID,
    val nick: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE) val joiningDate: Instant,
) {
    lateinit var roles: List<RoleShortDto>
    lateinit var user: UserDto
}

/**
 * Возвращает DTO представления с подробной информацией об участнике сервера.
 */
fun Member.toDetailsDto() = MemberDetailsDto(id, serverId, userId, nick, createdDate)