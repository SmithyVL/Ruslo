package ru.blimfy.gateway.mapper

import org.springframework.stereotype.Component
import ru.blimfy.domain.channel.db.entity.Invite
import ru.blimfy.domain.server.usecase.member.MemberService
import ru.blimfy.domain.server.usecase.server.ServerService
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.dto.invite.InviteDto

/**
 * Маппер для превращения приглашения в DTO и обратно.
 *
 * @property serverService сервис для работы с серверами.
 * @property memberService сервис для работы с участниками.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class InviteMapper(
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val userService: UserService,
) {
    suspend fun toDto(invite: Invite) =
        InviteDto(invite.id, invite.type).apply {
            channel = invite.channel.toPartialDto()
            inviter = userService.findUser(invite.authorId).toDto()

            invite.serverId?.let { serverId ->
                server = serverService.findServer(serverId).toPartialDto()
                approximateMemberCount = memberService.getCountServerMembers(serverId)
            }
        }
}