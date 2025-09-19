package tech.ruslo.gateway.api.channel.invite.handler

import java.util.UUID
import org.springframework.stereotype.Service
import tech.ruslo.domain.channel.db.entity.InviteTypes.GROUP_DM
import tech.ruslo.domain.channel.db.entity.InviteTypes.SERVER
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.channel.usecase.invite.InviteService
import tech.ruslo.domain.server.usecase.server.ServerService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.mapper.ChannelMapper
import tech.ruslo.gateway.mapper.InviteMapper
import tech.ruslo.gateway.mapper.MemberMapper
import tech.ruslo.gateway.mapper.ServerMapper
import tech.ruslo.websocket.dto.enums.SendEvents.CHANNEL_UPDATE
import tech.ruslo.websocket.dto.enums.SendEvents.INVITE_DELETE
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_CREATE
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_MEMBER_ADD
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о приглашениях.
 *
 * @property accessService сервис для работы с доступами.
 * @property inviteService сервис для работы с приглашениями.
 * @property channelService сервис для работы с каналами.
 * @property serverService сервис для работы с серверами.
 * @property channelMapper маппер для работы с каналами.
 * @property inviteMapper маппер для работы с приглашениями.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class InviteApiServiceImpl(
    private val accessService: AccessService,
    private val inviteService: InviteService,
    private val channelService: ChannelService,
    private val serverService: ServerService,
    private val channelMapper: ChannelMapper,
    private val inviteMapper: InviteMapper,
    private val memberMapper: MemberMapper,
    private val userWsStorage: UserWebSocketStorage,
    private val serverMapper: ServerMapper,
) : InviteApiService {
    override suspend fun findInvite(id: UUID) =
        inviteMapper.toDto(inviteService.findInvite(id))

    override suspend fun deleteInvite(id: UUID, user: User) {
        inviteService.findInvite(id).let { invite ->
            val serverId = invite.serverId!!

            // Можно удалить приглашение только для сервера и только его создателем (сервера).
            accessService.isServerOwner(serverId, user.id)

            inviteService.deleteInvite(id)

            userWsStorage.sendMessage(INVITE_DELETE.name, inviteMapper.toDto(invite))
        }
    }

    override suspend fun useInvite(id: UUID, user: User) {
        inviteService.findInvite(id).let { invite ->
            val userId = user.id

            when (invite.type) {
                SERVER -> {
                    val serverId = invite.serverId!!
                    accessService.hasServerBan(serverId, userId)

                    // Добавляем нового участника на сервер и отправляем другим участникам информацию о нём.
                    serverService.addNewMember(serverId, userId)
                        .let { memberMapper.toDto(it) }
                        .apply { userWsStorage.sendMessage(SERVER_MEMBER_ADD.name, this) }

                    // Отправляем пользователю всю информацию о его новом сервере.
                    serverService.findServer(serverId, withRoles = true, withMembers = true)
                        .let { serverMapper.toDto(it) }
                        .apply { userWsStorage.sendMessage(SERVER_CREATE.name, this) }
                }

                GROUP_DM -> {
                    channelService.addRecipients(invite.channelId, setOf(userId))
                        .let { channelMapper.toDto(it) }
                        .apply { userWsStorage.sendMessage(CHANNEL_UPDATE.name, this) }
                }
            }
        }
    }
}