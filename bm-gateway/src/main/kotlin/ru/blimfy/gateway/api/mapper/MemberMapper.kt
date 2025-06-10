package ru.blimfy.gateway.api.mapper

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import ru.blimfy.gateway.api.dto.MemberDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.server.db.entity.Member
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения участника сервера в DTO и обратно.
 *
 * @property memberRoleService сервис для работы с серверами.
 * @property roleService сервис для работы с участниками.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class MemberMapper(
    private val memberRoleService: MemberRoleService,
    private val roleService: RoleService,
    private val userService: UserService,
) {
    /**
     * Возвращает DTO представление [member] сервера для [user].
     */
    suspend fun toDto(member: Member, user: User) =
        member.toBasicDto().apply { this.user = user.toDto() }

    /**
     * Возвращает DTO представление [member] сервера.
     */
    suspend fun toDto(member: Member) =
        member.toBasicDto().apply { user = userService.findUser(member.userId).toDto() }

    /**
     * Возвращает DTO представление участника сервера с базовой информацией.
     */
    private suspend fun Member.toBasicDto() =
        MemberDto(id, serverId, nick, createdDate).apply {
            roles = memberRoleService.findMemberRoles(id)
                .map { roleService.findRole(it.roleId) }
                .map { it.toDto() }
                .toList()
        }
}