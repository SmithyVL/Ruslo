package ru.blimfy.gateway.service.user

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.common.enumeration.ChannelTypes.DM
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.toDto
import ru.blimfy.gateway.dto.server.ServerPartialDto
import ru.blimfy.gateway.dto.server.member.toDto
import ru.blimfy.gateway.dto.server.role.toDto
import ru.blimfy.gateway.dto.server.toPartialDto
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.dto.user.UsernameDto
import ru.blimfy.gateway.dto.user.channel.NewDmChannelDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.exception.GatewayErrors.INCORRECT_LEAVING_SERVER
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.util.GatewayUtils.checkUserPassword
import ru.blimfy.gateway.util.GatewayUtils.createUserToken
import ru.blimfy.security.service.TokenService
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
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
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
    private val userWsStorage: UserWebSocketStorage,
    private val encoder: PasswordEncoder,
    private val tokenService: TokenService,
) : UserApiService {
    override suspend fun modifyUser(modifyUser: ModifyUserDto, user: User): UserDto {
        val userId = user.id
        userService.modifyUser(userId, modifyUser.globalName, modifyUser.avatar, modifyUser.bannerColor)
        return userService.findUser(userId).toDto().apply { userWsStorage.sendMessage(USER_UPDATE, this) }
    }

    override suspend fun changeUsername(usernameInfo: UsernameDto, user: User): UserDto {
        checkUserPassword(encoder, usernameInfo.password, user.password)

        return userService.setUsername(user.id, usernameInfo.username)
            .toDto()
            .apply {
                userWsStorage.sendMessage(USER_UPDATE, this)
                token = createUserToken(tokenService, username, id).token
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

        if (serverService.findServer(serverId).ownerId == userId) {
            throw IncorrectDataException(INCORRECT_LEAVING_SERVER.msg.format(serverId))
        }

        memberService.deleteUserMember(userId = userId, serverId = serverId).apply {
            userWsStorage.sendMessage(SERVER_MEMBER_REMOVE, userId)
        }
    }

    override suspend fun createDmChannel(channel: NewDmChannelDto, user: User): ChannelDto {
        // Формируем итоговый список участников нового личного канала вместе с текущим пользователем.
        val recipients = channel.recipients + user.id

        // Если пользователь создает личный диалог, то сначала ищем нет ли такого диалога и возвращаем его, а иначе
        // формируем поле "ownerId", которое является пустым для личных диалогов.
        val ownerId = if (channel.type == DM) {
            val existedDm = channelService.findDm(recipients)
            if (existedDm != null) {
                return existedDm.toDtoWithData()
            }

            null
        } else {
            user.id
        }

        val newDmChannel = Channel(channel.type, ownerId).apply { this.recipients = recipients }
        return channelService.save(newDmChannel).toDtoWithData()
    }

    override fun findUserDmChannels(user: User) =
        channelService.findDmChannels(user.id)
            .map { dmChannel ->
                dmChannel.toDto().apply {
                    recipients = dmChannel.recipients!!.map { userId -> userService.findUser(userId).toDto() }
                }
            }

    /**
     * Возвращает DTO представление канала.
     */
    private suspend fun Channel.toDtoWithData() =
        this.toDto().apply {
            recipients = this@toDtoWithData.recipients
                ?.map { recipientId -> userService.findUser(recipientId) }
                ?.map(User::toDto)
        }
}