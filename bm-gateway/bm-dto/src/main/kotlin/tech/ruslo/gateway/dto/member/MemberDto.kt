package tech.ruslo.gateway.dto.member

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import tech.ruslo.common.json.INSTANT_FORMAT
import tech.ruslo.common.json.INSTANT_TIMEZONE
import tech.ruslo.gateway.dto.role.RoleDto
import tech.ruslo.gateway.dto.user.UserDto

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
data class MemberDto(
    val id: UUID,
    val serverId: UUID,
    val nick: String? = null,
    @param:JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE) val joinedAt: Instant,
) {
    lateinit var roles: List<RoleDto>
    lateinit var user: UserDto
}