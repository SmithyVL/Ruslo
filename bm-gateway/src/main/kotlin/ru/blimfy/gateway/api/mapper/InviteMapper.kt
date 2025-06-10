package ru.blimfy.gateway.api.mapper

import org.springframework.stereotype.Component
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.gateway.api.dto.InviteDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.dto.toPartialDto
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения приглашения в DTO и обратно.
 *
 * @property serverService сервис для работы с серверами.
 * @property memberService сервис для работы с участниками.
 * @property channelService сервис для работы с каналами.
 * @property userService сервис для работы с пользователями.
 * @property channelMapper маппер для работы с каналами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class InviteMapper(
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val channelService: ChannelService,
    private val userService: UserService,
    private val channelMapper: ChannelMapper,
) {
    suspend fun toDto(invite: Invite) =
        InviteDto(invite.id, invite.type).apply {
            channel = channelService.findChannel(invite.channelId).let { channelMapper.toPartialDto(it) }
            inviter = userService.findUser(invite.authorId).toDto()

            invite.serverId?.let { serverId ->
                server = serverService.findServer(serverId).toPartialDto(false)
                approximateMemberCount = memberService.getCountServerMembers(serverId)
            }
        }
}