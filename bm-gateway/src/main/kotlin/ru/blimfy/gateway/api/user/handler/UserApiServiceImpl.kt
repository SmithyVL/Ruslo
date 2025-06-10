package ru.blimfy.gateway.api.user.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.common.enumeration.ChannelTypes.DM
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.gateway.api.dto.ServerPartialDto
import ru.blimfy.gateway.api.dto.UserDto
import ru.blimfy.gateway.api.dto.channel.ChannelDto
import ru.blimfy.gateway.api.dto.channel.NewChannelDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.dto.toPartialDto
import ru.blimfy.gateway.api.mapper.ChannelMapper
import ru.blimfy.gateway.api.user.dto.ModifyUserDto
import ru.blimfy.gateway.api.user.dto.UsernameDto
import ru.blimfy.gateway.exception.GatewayErrors.INCORRECT_LEAVING_SERVER
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.service.AccessService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_REMOVE
import ru.blimfy.websocket.dto.WsMessageTypes.USER_UPDATE

/**
 * Реализация интерфейса для работы с обработкой запросов о пользователях.
 *
 * @property userService сервис для работы с пользователями.
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @property channelService сервис для работы с личными каналами.
 * @property accessService сервис для работы с доступами.
 * @property channelMapper маппер для работы с каналами.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserApiServiceImpl(
    private val userService: UserService,
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val roleService: RoleService,
    private val memberRoleService: MemberRoleService,
    private val channelService: ChannelService,
    private val accessService: AccessService,
    private val channelMapper: ChannelMapper,
    private val userWsStorage: UserWebSocketStorage,
) : UserApiService {
    override suspend fun modifyUser(modifyUser: ModifyUserDto, user: User) =
        userService
            .modifyUser(
                user.id,
                modifyUser.globalName,
                modifyUser.avatar,
                modifyUser.bannerColor,
            )
            .toDto()
            .apply { userWsStorage.sendMessage(USER_UPDATE, this) }

    override suspend fun changeUsername(usernameInfo: UsernameDto, user: User): UserDto {
        accessService.checkPassword(usernameInfo.password, user.password)

        return userService.setUsername(user.id, usernameInfo.username)
            .toDto()
            .apply {
                userWsStorage.sendMessage(USER_UPDATE, this)
                token = accessService.generateToken(username, id)
            }
    }

    override fun findUserServers(user: User): Flow<ServerPartialDto> {
        val userId = user.id
        return memberService.findUserMembers(userId)
            .map { serverService.findServer(it.serverId) }
            .map { it.toPartialDto(it.ownerId == userId) }
    }

    override suspend fun findMember(serverId: UUID, user: User) =
        memberService.findServerMember(serverId = serverId, userId = user.id)
            .toDto()
            .apply {
                this.user = user.toDto()
                roles = memberRoleService.findMemberRoles(id)
                    .map { roleService.findRole(it.roleId) }
                    .map { it.toDto() }
                    .toList()
            }

    override suspend fun leaveServer(serverId: UUID, user: User) {
        val userId = user.id

        if (accessService.isServerOwner(serverId = serverId, userId = userId)) {
            throw IncorrectDataException(INCORRECT_LEAVING_SERVER.msg.format(serverId))
        }

        memberService.deleteUserMember(userId = userId, serverId = serverId).apply {
            userWsStorage.sendMessage(SERVER_MEMBER_REMOVE, userId)
        }
    }

    override suspend fun createDmChannel(channelDto: NewChannelDto, user: User): ChannelDto {
        // Если пользователь создает личный диалог, то сначала ищем нет ли такого диалога и возвращаем его, а иначе
        // формируем поле "ownerId", которое является пустым для личных диалогов.
        val ownerId = if (channelDto.type == DM) {
            check(channelDto.recipients?.size == 1)
            channelService.findChannel(channelDto.recipients + user.id)?.let {
                return channelMapper.toDto(it)
            }
        } else {
            user.id
        }

        return channelMapper.toEntity(channelDto, ownerId)
            .apply { channelService.save(this) }
            .let { channelMapper.toDto(it) }
    }

    override fun findUserDmChannels(user: User) =
        channelService.findDmChannels(user.id).map { channelMapper.toDto(it) }
}