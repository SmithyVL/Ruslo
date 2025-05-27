package ru.blimfy.gateway.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.server.db.entity.Member

/**
 * DTO с информацией об участнике сервера.
 *
 * @property id идентификатор участника.
 * @property serverId идентификатор сервера.
 * @property nick имя пользователя участника сервера.
 * @property joinedAt дата присоединения к серверу.
 * @property roles роли участника.
 * @property user информация об участнике сервера как пользователе.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonInclude(NON_NULL)
data class MemberDto(
    val id: UUID,
    val serverId: UUID,
    val nick: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE) val joinedAt: Instant,
) {
    lateinit var roles: List<RoleDto>
    lateinit var user: UserDto
}

/**
 * Возвращает DTO представления с информацией об участнике сервера.
 */
fun Member.toDto() = MemberDto(id, serverId, nick, createdDate)