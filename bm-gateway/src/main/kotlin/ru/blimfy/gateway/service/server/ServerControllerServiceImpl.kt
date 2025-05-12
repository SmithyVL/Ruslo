package ru.blimfy.gateway.service.server

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.toDto
import ru.blimfy.gateway.dto.invite.InviteDto
import ru.blimfy.gateway.dto.invite.toDto
import ru.blimfy.gateway.dto.member.MemberDetailsDto
import ru.blimfy.gateway.dto.member.toDetailsDto
import ru.blimfy.gateway.dto.role.toDto
import ru.blimfy.gateway.dto.server.NewServerDto
import ru.blimfy.gateway.dto.server.ServerDto
import ru.blimfy.gateway.dto.server.toDto
import ru.blimfy.gateway.dto.server.toEntity
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.channel.ChannelService
import ru.blimfy.server.usecase.invite.InviteService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_SERVER
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_SERVER_MEMBER

/**
 * Реализация интерфейса для работы с обработкой запросов о серверах.
 *
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberRoleService сервис для работы с участниками ролей серверов.
 * @property inviteService сервис для работы с приглашениями серверов.
 * @property userService сервис для работы с пользователями.
 * @property userTokenWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerControllerServiceImpl(
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val memberService: MemberService,
    private val roleService: RoleService,
    private val memberRoleService: MemberRoleService,
    private val inviteService: InviteService,
    private val userService: UserService,
    private val userTokenWebSocketStorage: UserWebSocketStorage,
) : ServerControllerService {
    override suspend fun createServer(newServerDto: NewServerDto, user: CustomUserDetails) =
        serverService.createServer(newServerDto.toEntity(user.userInfo.id)).toDto()

    override suspend fun modifyServer(serverDto: ServerDto, user: CustomUserDetails): ServerDto {
        val userId = user.userInfo.id
        val serverId = serverDto.id

        // Обновить сервер может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        return serverService.modifyServer(serverDto.toEntity(userId)).toDto()
            .apply { userTokenWebSocketStorage.sendServerMessages(serverId, EDIT_SERVER, this, userId) }
    }

    override suspend fun findServer(serverId: UUID, user: CustomUserDetails): ServerDto {
        // Получить сервер может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = user.userInfo.id)

        return serverService.findServer(serverId).toDto()
    }

    override suspend fun deleteServer(serverId: UUID, user: CustomUserDetails) =
        serverService.deleteServer(serverId = serverId, ownerId = user.userInfo.id)

    override suspend fun deleteServerMember(serverId: UUID, memberId: UUID, user: CustomUserDetails) {
        val userId = user.userInfo.id

        // Кикнуть участника сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        memberService.deleteServerMember(memberId = memberId, serverId = serverId)
            .apply { userTokenWebSocketStorage.sendServerMessages(serverId, REMOVE_SERVER_MEMBER, memberId, userId) }
    }

    override suspend fun findServerMembers(serverId: UUID, user: CustomUserDetails): Flow<MemberDetailsDto> {
        // Получить участников сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = user.userInfo.id)

        return memberService.findServerMembers(serverId)
            .map { it.toDetailsDto() }
            .onEach { member ->
                member.apply {
                    roles = memberRoleService.findMemberRoles(id)
                        .map { roleService.findRole(it.roleId) }
                        // Пропускаем дефолтную роль сервера.
                        .filter { it.position != 0 }
                        .map { it.toDto() }
                        .toList()

                    this.user = userService.findUser(userId).toDto()
                }
            }
    }

    override suspend fun findServerChannels(serverId: UUID, user: CustomUserDetails): Flow<ChannelDto> {
        // Получить каналы сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = user.userInfo.id)

        return channelService.findServerChannels(serverId).map { it.toDto() }
    }

    override suspend fun findServerInvites(serverId: UUID, user: CustomUserDetails): Flow<InviteDto> {
        // Получить приглашения сервера может только создатель сервера.
        serverService.checkServerModifyAccess(serverId = serverId, userId = user.userInfo.id)

        return inviteService.findServerInvites(serverId).map { it.toDto() }
    }
}