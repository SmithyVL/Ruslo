package ru.blimfy.gateway.api.mapper

import java.util.UUID
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.gateway.api.dto.MemberDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.integration.websockets.base.PartialMemberDto
import ru.blimfy.gateway.integration.websockets.extra.MemberInfoDto
import ru.blimfy.server.db.entity.Member
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения участника сервера в DTO и обратно.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property memberRoleService сервис для работы с серверами.
 * @property roleService сервис для работы с участниками.
 * @property roleService сервис для работы с каналами.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class MemberMapper(
    private val memberService: MemberService,
    private val memberRoleService: MemberRoleService,
    private val roleService: RoleService,
    private val channelService: ChannelService,
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
     * Возвращает DTO представление с информацией об участнике сервера с [userId], к которому относится канал с
     * [channelId].
     */
    suspend fun toWsDto(channelId: UUID, userId: UUID) =
        channelService.findChannel(channelId).serverId
            ?.let { serverId -> memberService.findServerMember(serverId, userId) }
            ?.let { member -> MemberInfoDto(member.serverId, PartialMemberDto(member.nick)) }

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