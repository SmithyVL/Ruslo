package ru.blimfy.gateway.api.channel.invite.handler

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.InviteTypes.GROUP_DM
import ru.blimfy.common.enumeration.InviteTypes.SERVER
import ru.blimfy.gateway.api.mapper.ChannelMapper
import ru.blimfy.gateway.api.mapper.InviteMapper
import ru.blimfy.gateway.api.mapper.MemberMapper
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.integration.websockets.base.EntityDeleteDto
import ru.blimfy.server.usecase.ban.BanService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.WsMessageTypes.INVITE_DELETE
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_ADD

/**
 * Реализация интерфейса для работы с обработкой запросов о приглашениях.
 *
 * @property inviteService сервис для работы с приглашениями.
 * @property channelService сервис для работы с каналами.
 * @property serverService сервис для работы с серверами.
 * @property banService сервис для работы с банами серверов.
 * @property channelMapper маппер для работы с каналами.
 * @property inviteMapper маппер для работы с приглашениями.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class InviteApiServiceImpl(
    private val inviteService: InviteService,
    private val channelService: ChannelService,
    private val serverService: ServerService,
    private val banService: BanService,
    private val channelMapper: ChannelMapper,
    private val inviteMapper: InviteMapper,
    private val memberMapper: MemberMapper,
    private val userWsStorage: UserWebSocketStorage,
) : InviteApiService {
    override suspend fun findInvite(id: UUID) =
        inviteMapper.toDto(inviteService.findInvite(id))

    override suspend fun deleteInvite(id: UUID, user: User) =
        inviteService.findInvite(id)
            .apply {
                // Удалить приглашение на сервер может только его создатель.
                serverService.checkServerWrite(serverId!!, user.id)
            }
            .let { inviteService.deleteInvite(id) }
            .let { inviteMapper.toDto(it) }
            .apply {
                val data = EntityDeleteDto(id, this.channel.id, this.server?.id)
                userWsStorage.sendMessage(INVITE_DELETE, data)
            }

    override suspend fun useInvite(id: UUID, user: User) {
        inviteService.findInvite(id).let { invite ->
            when (invite.type) {
                SERVER ->
                    if (banService.findBan(invite.serverId!!, user.id) == null) {
                        serverService.addNewMember(invite.serverId!!, user.id)
                            .let { memberMapper.toDto(it, user) }
                            .apply { userWsStorage.sendMessage(SERVER_MEMBER_ADD, this) }
                    }

                GROUP_DM -> channelService.addRecipients(invite.channelId, setOf(user.id))
                    .let { channelMapper.toDto(it) }
                    .apply { userWsStorage.sendMessage(CHANNEL_UPDATE, this) }
            }
        }
    }
}