package ru.blimfy.gateway.access.control.service

import java.util.UUID
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.domain.channel.db.entity.Channel
import ru.blimfy.domain.channel.db.entity.ChannelGroups.SERVER
import ru.blimfy.domain.channel.db.entity.ChannelGroups.USER
import ru.blimfy.domain.channel.usecase.channel.ChannelService
import ru.blimfy.domain.server.usecase.ban.BanService
import ru.blimfy.domain.server.usecase.member.MemberService
import ru.blimfy.domain.server.usecase.server.ServerService
import ru.blimfy.gateway.access.control.exception.AccessErrors.DM_VIEW_ACCESS_DENIED
import ru.blimfy.gateway.access.control.exception.AccessErrors.GROUP_ACCESS_DENIED
import ru.blimfy.gateway.access.control.exception.AccessErrors.INCORRECT_PASSWORD
import ru.blimfy.gateway.access.control.exception.AccessErrors.SERVER_ACCESS_DENIED
import ru.blimfy.gateway.access.control.exception.AccessErrors.SERVER_VIEW_ACCESS_DENIED
import ru.blimfy.security.service.TokenService

/**
 * Реализация интерфейса для работы с разрешениями, токенами и шифрованием/дешифрованием паролей.
 *
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами.
 * @property memberService сервис для работы с участниками серверов.
 * @property banService сервис для работы с банами серверов.
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
    private val banService: BanService,
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
            memberService.findMember(serverId, userId)
        } catch (_: NotFoundException) {
            throw AccessDeniedException(SERVER_VIEW_ACCESS_DENIED.msg.format(serverId))
        }
    }

    override suspend fun hasServerBan(serverId: UUID, userId: UUID) {
        if (banService.findBan(serverId, userId) != null) {
            throw AccessDeniedException(SERVER_ACCESS_DENIED.msg.format(serverId))
        }
    }

    override fun checkPassword(checkPassword: String, password: String) {
        if (!encoder.matches(checkPassword, password)) {
            throw IllegalArgumentException(INCORRECT_PASSWORD.msg.format(checkPassword))
        }
    }

    override fun encodePassword(password: String): String =
        encoder.encode(password)

    override fun generateToken(username: String, userId: UUID) =
        tokenService.generateToken(username, userId)

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