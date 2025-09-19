package tech.ruslo.gateway.api.user.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import tech.ruslo.domain.channel.api.dto.channel.NewChannelDto
import tech.ruslo.domain.channel.db.entity.ChannelTypes
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.server.usecase.member.MemberService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.domain.user.usecase.user.UserService
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.dto.channel.ChannelDto
import tech.ruslo.gateway.dto.user.ModifyUserDto
import tech.ruslo.gateway.dto.user.UserDto
import tech.ruslo.gateway.dto.user.UsernameDto
import tech.ruslo.gateway.mapper.ChannelMapper
import tech.ruslo.gateway.mapper.MemberMapper
import tech.ruslo.gateway.mapper.toDto
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_MEMBER_REMOVE
import tech.ruslo.websocket.dto.enums.SendEvents.USER_UPDATE
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о пользователях.
 *
 * @property userService сервис для работы с пользователями.
 * @property memberService сервис для работы с участниками серверов.
 * @property channelService сервис для работы с личными каналами.
 * @property channelMapper маппер для работы с каналами.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property accessService сервис для работы с доступами.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserApiServiceImpl(
    private val userService: UserService,
    private val memberService: MemberService,
    private val channelService: ChannelService,
    private val channelMapper: ChannelMapper,
    private val memberMapper: MemberMapper,
    private val accessService: AccessService,
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
            .apply { userWsStorage.sendMessage(USER_UPDATE.name, this) }

    override suspend fun changeUsername(usernameInfo: UsernameDto, user: User): UserDto {
        accessService.checkPassword(usernameInfo.password, user.password)

        return userService.setUsername(user.id, usernameInfo.username)
            .toDto()
            .apply {
                userWsStorage.sendMessage(USER_UPDATE.name, this)
                token = accessService.generateToken(username, id)
            }
    }

    override suspend fun findMember(serverId: UUID, user: User) =
        memberService.findMember(serverId, user.id)
            .let { memberMapper.toDto(it) }

    override suspend fun leaveServer(serverId: UUID, user: User) {
        user.id.apply {
            accessService.isServerOwner(serverId, this)
                .let { memberService.deleteUserMember(this, serverId) }
                .apply { userWsStorage.sendMessage(SERVER_MEMBER_REMOVE.name, this) }
        }
    }

    override suspend fun createDmChannel(channelDto: NewChannelDto, user: User): ChannelDto {
        // Если пользователь создает личный диалог, то сначала ищем нет ли такого диалога и возвращаем его, а иначе
        // формируем поле "ownerId", которое является пустым для личных диалогов.
        val ownerId = if (channelDto.type == ChannelTypes.DM.name) {
            check(channelDto.recipients?.size == 1)
            channelService.findChannel(channelDto.recipients!! + user.id)?.let {
                return channelMapper.toDto(it)
            }
        } else {
            user.id
        }

        return channelService.createChannel(channelDto, ownerId)
            .let { channelMapper.toDto(it) }
    }

    override fun findUserDmChannels(user: User) =
        channelService.findDmChannels(user.id).map { channelMapper.toDto(it) }
}