package ru.blimfy.gateway.service

import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.common.enumeration.ChannelGroups.SERVER
import ru.blimfy.common.enumeration.ChannelGroups.USER
import ru.blimfy.common.exception.AccessDeniedException
import ru.blimfy.common.exception.IncorrectDataException
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.gateway.exception.GatewayErrors.DM_VIEW_ACCESS_DENIED
import ru.blimfy.gateway.exception.GatewayErrors.GROUP_ACCESS_DENIED
import ru.blimfy.gateway.exception.GatewayErrors.INCORRECT_PASSWORD
import ru.blimfy.gateway.exception.GatewayErrors.SERVER_ACCESS_DENIED
import ru.blimfy.gateway.exception.GatewayErrors.SERVER_VIEW_ACCESS_DENIED
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService

/**
 * Реализация интерфейса для работы с разрешениями, токенами и шифрованием/дешифрованием паролей.
 *
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами.
 * @property memberService сервис для работы с участниками серверов.
 * @property tokenService сервис для работы с токенами.
 * @property encoder компонент для получения хэша пароля.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class AccessServiceImpl(
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val memberService: MemberService,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder,
) : AccessService {
    override suspend fun isChannelViewAccess(channelId: UUID, userId: UUID) {
        channelService.findChannel(channelId).let {
            when (it.type.group) {
                USER -> isDmViewAccess(it, userId)
                SERVER -> isServerMember(it.serverId!!, userId)
            }
        }
    }

    override suspend fun isChannelWriteAccess(channelId: UUID, userId: UUID) {
        channelService.findChannel(channelId).let {
            when (it.type.group) {
                USER -> isGroupOwner(it, userId)
                SERVER -> isServerOwner(it.serverId!!, userId)
            }
        }
    }

    override suspend fun isPinsWriteAccess(channelId: UUID, userId: UUID) {
        channelService.findChannel(channelId).let {
            when (it.type.group) {
                USER -> isDmViewAccess(it, userId)
                SERVER -> isServerOwner(it.serverId!!, userId)
            }
        }
    }

    override suspend fun isServerOwner(serverId: UUID, userId: UUID) {
        serverService.findServer(serverId).let {
            if (it.ownerId != userId) {
                throw AccessDeniedException(SERVER_ACCESS_DENIED.msg.format(serverId))
            }
        }
    }

    override suspend fun isServerMember(serverId: UUID, userId: UUID) {
        try {
            memberService.findServerMember(serverId, userId)
        } catch (_: NotFoundException) {
            throw AccessDeniedException(SERVER_VIEW_ACCESS_DENIED.msg.format(serverId))
        }
    }

    override fun checkPassword(checkPassword: String, password: String) {
        if (!encoder.matches(checkPassword, password)) {
            throw IncorrectDataException(INCORRECT_PASSWORD.msg.format(checkPassword))
        }
    }

    override fun encodePassword(password: String): String = encoder.encode(password)

    override fun generateToken(username: String, userId: UUID) = tokenService.generateToken(username, userId)

    /**
     * Есть ли у пользователя с [userId] доступ к просмотру личного [channel].
     */
    private fun isDmViewAccess(channel: Channel, userId: UUID) {
        if (!channel.recipients!!.contains(userId) && channel.ownerId != userId) {
            throw AccessDeniedException(DM_VIEW_ACCESS_DENIED.msg.format(channel.id))
        }
    }

    /**
     * Является ли пользователь с [userId] владельцем [channel].
     */
    private fun isGroupOwner(channel: Channel, userId: UUID) {
        if (channel.ownerId != userId) {
            throw AccessDeniedException(GROUP_ACCESS_DENIED.msg.format(channel.id))
        }
    }
}