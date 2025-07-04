package ru.blimfy.gateway.mapper

import org.springframework.stereotype.Component
import ru.blimfy.domain.server.db.entity.Member
import ru.blimfy.domain.server.db.entity.Role
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.dto.member.MemberDto
import ru.blimfy.gateway.dto.websockets.PartialMemberDto

/**
 * Маппер для превращения участника сервера в DTO и обратно.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class MemberMapper(private val userService: UserService) {
    /**
     * Возвращает DTO представление [member] сервера.
     */
    suspend fun toDto(member: Member) =
        member.toBasicDto()

    /**
     * Возвращает DTO представление участника сервера с базовой информацией.
     */
    private suspend fun Member.toBasicDto() =
        MemberDto(id, serverId, nick, createdDate).apply {
            roles = this@toBasicDto.roles.map(Role::toDto)
            user = userService.findUser(userId).toDto()
        }
}

/**
 * Возвращает DTO представление участника сервера с частичной информацией.
 */
fun Member.toPartialDto() =
    PartialMemberDto(serverId, nick)