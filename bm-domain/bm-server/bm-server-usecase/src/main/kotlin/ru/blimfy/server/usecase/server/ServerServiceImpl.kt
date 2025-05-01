package ru.blimfy.server.usecase.server

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.common.enumeration.ChannelTypes.TEXT
import ru.blimfy.common.enumeration.ChannelTypes.VOICE
import ru.blimfy.common.exception.AccessDeniedException
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.server.db.entity.Channel
import ru.blimfy.server.db.entity.Member
import ru.blimfy.server.db.entity.MemberRole
import ru.blimfy.server.db.entity.Role
import ru.blimfy.server.db.entity.Server
import ru.blimfy.server.db.repository.ServerRepository
import ru.blimfy.server.usecase.channel.ChannelService
import ru.blimfy.server.usecase.exception.ServerErrors.SERVER_BY_ID_NOT_FOUND
import ru.blimfy.server.usecase.exception.ServerErrors.SERVER_MODIFY_ACCESS_DENIED
import ru.blimfy.server.usecase.exception.ServerErrors.SERVER_VIEW_ACCESS_DENIED
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.server.usecase.role.RoleServiceImpl.Companion.DEFAULT_ROLE_NAME

/**
 * Реализация интерфейса для работы с серверами пользователей.
 *
 * @property serverRepo репозиторий для работы с сущностями серверов в БД.
 * @property channelService сервис для работы с каналами серверов.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerServiceImpl(
    private val serverRepo: ServerRepository,
    private val channelService: ChannelService,
    private val roleService: RoleService,
    private val memberService: MemberService,
    private val memberRoleService: MemberRoleService,
) : ServerService {
    @Transactional
    override suspend fun createServer(server: Server) =
        serverRepo.save(server).apply {
            val serverId = this.id

            // Создание дефолтных каналов для нового сервера - текстового и голосового.
            channelService.saveChannel(Channel(serverId, DEFAULT_TEXT_CHANNEL_NAME, TEXT))
            channelService.saveChannel(Channel(serverId, DEFAULT_VOICE_CHANNEL_NAME, VOICE))

            // Создание дефолтной роли для нового сервера, которая будет присваиваться каждому нового участнику
            // навсегда.
            val defaultRoleId = roleService.createRole(Role(serverId, DEFAULT_ROLE_NAME, true)).id

            // Создание участника для пользователя-создателя сервера с дефолтной ролью.
            val memberId = memberService.saveMember(Member(serverId = serverId, userId = this.ownerId)).id
            memberRoleService.saveRoleToMember(MemberRole(memberId = memberId, roleId = defaultRoleId))
        }

    override suspend fun modifyServer(server: Server) = serverRepo.save(server)

    override suspend fun findServer(id: UUID) = serverRepo.findById(id)
        ?: throw NotFoundException(SERVER_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun deleteServer(id: UUID, ownerId: UUID) =
        serverRepo.deleteByIdAndOwnerId(id = id, ownerId = ownerId)

    override suspend fun addNewMember(serverId: UUID, userId: UUID) =
        memberService.saveMember(Member(serverId = serverId, userId = userId)).apply {
            val defaultRoleId = roleService.findDefaultServerRole(serverId).id
            memberRoleService.saveRoleToMember(MemberRole(memberId = id, roleId = defaultRoleId))
        }

    override suspend fun checkServerModifyAccess(serverId: UUID, userId: UUID) {
        val server = findServer(serverId)

        if (userId != server.ownerId) {
            throw AccessDeniedException(SERVER_MODIFY_ACCESS_DENIED.msg.format(server.id))
        }
    }

    override suspend fun checkServerViewAccess(serverId: UUID, userId: UUID) {
        try {
            memberService.findServerMember(userId = userId, serverId = serverId)
        } catch (_: NotFoundException) {
            throw AccessDeniedException(SERVER_VIEW_ACCESS_DENIED.msg.format(serverId))
        }
    }

    private companion object {
        /**
         * Стандартное название текстового канала.
         */
        const val DEFAULT_TEXT_CHANNEL_NAME = "Текстовый канал"

        /**
         * Стандартное название голосового канала.
         */
        const val DEFAULT_VOICE_CHANNEL_NAME = "Голосовой канал"
    }
}