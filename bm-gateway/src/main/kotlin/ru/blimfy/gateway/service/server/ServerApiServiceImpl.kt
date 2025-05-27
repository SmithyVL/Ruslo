package ru.blimfy.gateway.service.server

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.invite.InviteService
import ru.blimfy.common.enumeration.ChannelTypes.TEXT
import ru.blimfy.common.enumeration.ChannelTypes.VOICE
import ru.blimfy.common.enumeration.InviteTypes.SERVER
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.invite.InviteDto
import ru.blimfy.gateway.dto.channel.invite.toDto
import ru.blimfy.gateway.dto.channel.toDto
import ru.blimfy.gateway.dto.channel.toPartialDto
import ru.blimfy.gateway.dto.server.ModifyServerDto
import ru.blimfy.gateway.dto.server.NewServerDto
import ru.blimfy.gateway.dto.server.ServerDto
import ru.blimfy.gateway.dto.server.channel.ChannelPositionDto
import ru.blimfy.gateway.dto.server.channel.ServerChannelDto
import ru.blimfy.gateway.dto.server.channel.toEntity
import ru.blimfy.gateway.dto.server.toDto
import ru.blimfy.gateway.dto.server.toEntity
import ru.blimfy.gateway.dto.server.toPartialDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_CREATE
import ru.blimfy.websocket.dto.WsMessageTypes.CHANNEL_UPDATE
import ru.blimfy.websocket.dto.WsMessageTypes.INVITE_CREATE
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_UPDATE

/**
 * Реализация интерфейса для работы с обработкой запросов о серверах.
 *
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @property inviteService сервис для работы с приглашениями серверов.
 * @property userService сервис для работы с пользователями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerApiServiceImpl(
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val memberService: MemberService,
    private val inviteService: InviteService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : ServerApiService {
    @Transactional
    override suspend fun createServer(newServer: NewServerDto, user: User) =
        serverService.createServer(newServer.toEntity(user.id))
            .apply { createDefaultServerChannels(id) }
            .toDto()

    override suspend fun findServer(id: UUID, user: User): ServerDto {
        // Получить сервер может только его участник.
        serverService.checkServerViewAccess(serverId = id, userId = user.id)

        return serverService.findServer(id).toDto()
    }

    override suspend fun modifyServer(id: UUID, modifyServer: ModifyServerDto, user: User): ServerDto {
        val userId = user.id

        // Обновить сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = id, userId = userId)

        return serverService
            .modifyServer(id, modifyServer.name, modifyServer.icon, modifyServer.bannerColor, modifyServer.description)
            .toDto()
            .apply { userWsStorage.sendMessage(SERVER_UPDATE, this) }
    }

    override suspend fun changeOwner(id: UUID, userId: UUID, user: User): ServerDto {
        // Изменить владельца сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = id, userId = user.id)

        return serverService
            .setOwner(id = id, ownerId = userId)
            .toDto()
            .apply { userWsStorage.sendMessage(SERVER_UPDATE, this) }
    }

    override suspend fun deleteServer(id: UUID, user: User) =
        serverService.deleteServer(serverId = id, ownerId = user.id)

    override suspend fun findServerChannels(id: UUID, user: User): Flow<ChannelDto> {
        // Получить каналы сервера может только его участник.
        serverService.checkServerViewAccess(serverId = id, userId = user.id)

        return channelService.findServerChannels(id).map { it.toDto() }
    }

    override suspend fun createChannel(id: UUID, channel: ServerChannelDto, user: User): ChannelDto {
        // Создать канал сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = id, userId = user.id)

        return channelService.save(channel.toEntity(id)).toDto()
            .apply { userWsStorage.sendMessage(CHANNEL_CREATE, this) }
    }

    override suspend fun modifyServerChannelPositions(id: UUID, positions: List<ChannelPositionDto>, user: User) {
        // Изменить позиции каналов сервера может только его создатель.
        serverService.checkServerModifyAccess(serverId = id, userId = user.id)

        positions.forEach {
            channelService.findChannel(it.id).apply {
                position = it.position
                parentId = it.parentId
                channelService.save(this)
                userWsStorage.sendMessage(CHANNEL_UPDATE, this)
            }
        }
    }

    override suspend fun findServerInvites(id: UUID, user: User): Flow<InviteDto> {
        // Получить приглашения сервера может только его создатель.
        serverService.checkServerModifyAccess(serverId = id, userId = user.id)

        return inviteService.findInvites(id, SERVER).map { it.toDtoWithLinkData() }
    }

    override suspend fun createInvite(id: UUID, channelId: UUID, user: User) =
        serverService.checkServerModifyAccess(serverId = id, userId = user.id)
            .let { Invite(authorId = user.id, channelId = id, type = SERVER).apply { serverId = id } }
            .let { inviteService.saveInvite(it) }
            .toDtoWithLinkData()
            .apply { userWsStorage.sendMessage(INVITE_CREATE, this) }

    /**
     * Создаёт набор стандартных каналов для сервера с [serverId]
     */
    private suspend fun createDefaultServerChannels(serverId: UUID) {
        Channel(TEXT, serverId).apply {
            name = SERVER_DEFAULT_TEXT_CHANNEL
            channelService.save(this)
        }

        Channel(VOICE, serverId).apply {
            name = SERVER_DEFAULT_VOICE_CHANNEL
            channelService.save(this)
        }
    }

    /**
     * Возвращает DTO представление приглашения.
     */
    private suspend fun Invite.toDtoWithLinkData() = this.toDto().apply {
        channel = channelService.findChannel(channelId).toPartialDto()
        inviter = userService.findUser(authorId).toDto()

        serverId?.let { serverId ->
            server = serverService.findServer(serverId).toPartialDto(false)
            approximateMemberCount = memberService.getCountServerMembers(serverId)
        }
    }

    private companion object {
        /**
         * Стандартное название текстового канала сервера.
         */
        const val SERVER_DEFAULT_TEXT_CHANNEL = "Текстовый канал"

        /**
         * Стандартное название голосового канала сервера.
         */
        const val SERVER_DEFAULT_VOICE_CHANNEL = "Голосовой канал"
    }
}