package tech.ruslo.gateway.mapper

import org.springframework.stereotype.Component
import tech.ruslo.domain.channel.db.entity.Invite
import tech.ruslo.domain.server.usecase.member.MemberService
import tech.ruslo.domain.server.usecase.server.ServerService
import tech.ruslo.domain.user.usecase.user.UserService
import tech.ruslo.gateway.dto.invite.InviteDto

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