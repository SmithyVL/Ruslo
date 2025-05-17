package ru.blimfy.gateway.service.user

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.blimfy.direct.usecase.channel.DmChannelService
import ru.blimfy.gateway.dto.dm.channel.toDto
import ru.blimfy.gateway.dto.server.ServerShortDto
import ru.blimfy.gateway.dto.server.member.toDto
import ru.blimfy.gateway.dto.server.role.toDto
import ru.blimfy.gateway.dto.server.toShortDto
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.dto.user.UsernameDto
import ru.blimfy.gateway.dto.user.friend.toDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.util.GatewayUtils.checkUserPassword
import ru.blimfy.gateway.util.GatewayUtils.createUserToken
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.friend.FriendService
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_SERVER_MEMBER

/**
 * Реализация интерфейса для работы с обработкой запросов о пользователях.
 *
 * @property userService сервис для работы с пользователями.
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @property dmChannelService сервис для работы с личными каналами.
 * @property friendService сервис для работы с друзьями.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserControllerServiceImpl(
    private val userService: UserService,
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val roleService: RoleService,
    private val memberRoleService: MemberRoleService,
    private val dmChannelService: DmChannelService,
    private val friendService: FriendService,
    private val userWebSocketStorage: UserWebSocketStorage,
    private val encoder: PasswordEncoder,
    private val tokenService: TokenService,
) : UserControllerService {
    override suspend fun modifyUser(modifyUser: ModifyUserDto, currentUser: User): UserDto {
        val userId = currentUser.id
        userService.modifyUser(userId, modifyUser.globalName, modifyUser.avatar, modifyUser.bannerColor)
        return userService.findUser(userId).toDto()
    }

    override suspend fun changeUsername(usernameInfo: UsernameDto, currentUser: User): UserDto {
        checkUserPassword(encoder, usernameInfo.password, currentUser.password)

        val userId = currentUser.id
        userService.setUsername(userId, usernameInfo.username)
        return userService.findUser(userId)
            .toDto()
            .apply { token = createUserToken(tokenService, username, userId).token }
    }

    override fun findUserServers(currentUser: User): Flow<ServerShortDto> {
        val userId = currentUser.id
        return memberService.findUserMembers(userId)
            .map { serverService.findServer(it.serverId) }
            .map { it.toShortDto(it.ownerId == userId) }
    }

    override suspend fun findServerMember(serverId: UUID, currentUser: User) =
        memberService.findServerMember(serverId = serverId, userId = currentUser.id)
            .toDto()
            .apply {
                user = currentUser.toDto()
                roles = memberRoleService.findMemberRoles(id)
                    .map { roleService.findRole(it.roleId) }
                    .map { it.toDto() }
                    .toList()
            }

    override suspend fun leaveServer(serverId: UUID, currentUser: User) {
        val userId = currentUser.id

        memberService.deleteUserMember(userId = userId, serverId = serverId).apply {
            userWebSocketStorage.sendServerMessages(serverId, REMOVE_SERVER_MEMBER, userId, userId)
        }
    }

    override fun findUserDmChannels(currentUser: User) =
        dmChannelService.findDmChannels(currentUser.id)
            .map { dmChannel ->
                dmChannel.toDto().apply {
                    recipients = dmChannel.recipients.map { userId -> userService.findUser(userId).toDto() }
                }
            }

    override fun findUserFriends(currentUser: User) =
        friendService.findFriends(currentUser.id)
            .map { friend -> friend.toDto().apply { to = userService.findUser(friend.toId).toDto() } }
}