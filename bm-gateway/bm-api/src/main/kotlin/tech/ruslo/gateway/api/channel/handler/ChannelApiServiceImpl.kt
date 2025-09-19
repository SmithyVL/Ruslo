package tech.ruslo.gateway.api.channel.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import tech.ruslo.domain.channel.api.dto.channel.ModifyChannelDto
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.channel.usecase.invite.InviteService
import tech.ruslo.domain.server.usecase.member.MemberService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.dto.websockets.PartialMemberDto
import tech.ruslo.gateway.dto.websockets.TypingStartDto
import tech.ruslo.gateway.mapper.ChannelMapper
import tech.ruslo.gateway.mapper.InviteMapper
import tech.ruslo.websocket.dto.enums.SendEvents.CHANNEL_DELETE
import tech.ruslo.websocket.dto.enums.SendEvents.CHANNEL_UPDATE
import tech.ruslo.websocket.dto.enums.SendEvents.TYPING_START
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о личных каналах.
 *
 * @property accessService сервис для работы с доступами.
 * @property channelService сервис для работы с каналами.
 * @property memberService сервис для работы с участниками серверов.
 * @property inviteService сервис для работы с приглашениями.
 * @property channelMapper маппер для работы с каналами.
 * @property inviteMapper маппер для работы с приглашениями.
 * @property wsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelApiServiceImpl(
    private val accessService: AccessService,
    private val channelService: ChannelService,
    private val memberService: MemberService,
    private val inviteService: InviteService,
    private val channelMapper: ChannelMapper,
    private val inviteMapper: InviteMapper,
    private val wsStorage: UserWebSocketStorage,
) : ChannelApiService {
    override suspend fun modifyChannel(id: UUID, modifyChannel: ModifyChannelDto, user: User) =
        accessService.isChannelWriteAccess(id, user.id)
            .let { channelService.modifyChannel(id, modifyChannel) }
            .let { channelMapper.toDto(it) }
            .apply { wsStorage.sendMessage(CHANNEL_UPDATE.name, this) }

    override suspend fun deleteChannel(id: UUID, user: User) {
        accessService.isChannelWriteAccess(id, user.id)
            .let { channelService.deleteChannel(id) }
            .let { channelMapper.toDto(it) }
            .apply { wsStorage.sendMessage(CHANNEL_DELETE.name, this) }
    }

    override suspend fun triggerTypingIndicator(id: UUID, user: User) {
        user.id.let { userId ->
            channelService.findChannel(id).serverId
                ?.let { memberService.findMember(it, userId, true) }
                ?.let { PartialMemberDto(it.serverId, it.nick) }
                ?.let { TypingStartDto(id, userId).apply { member = it } }
                ?.let { wsStorage.sendMessage(TYPING_START.name, it) }
        }
    }

    override suspend fun findInvites(id: UUID, user: User) =
        accessService.isChannelViewAccess(id, user.id)
            .let { inviteService.findChannelInvites(id) }
            .map { inviteMapper.toDto(it) }
}