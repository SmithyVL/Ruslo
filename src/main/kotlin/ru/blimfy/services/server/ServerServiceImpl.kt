package ru.blimfy.services.server

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.common.enums.ChannelTypes.TEXT
import ru.blimfy.common.enums.ChannelTypes.VOICE
import ru.blimfy.exception.Errors.SERVER_BY_ID_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.Channel
import ru.blimfy.persistence.entity.Member
import ru.blimfy.persistence.entity.MemberRole
import ru.blimfy.persistence.entity.Role
import ru.blimfy.persistence.entity.Server
import ru.blimfy.persistence.repository.ServerRepository
import ru.blimfy.services.channel.ChannelService
import ru.blimfy.services.member.MemberService
import ru.blimfy.services.member.role.MemberRoleService
import ru.blimfy.services.role.RoleService
import ru.blimfy.services.role.RoleServiceImpl.Companion.DEFAULT_ROLE_NAME

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
    override suspend fun saveServer(server: Server): Server {
        val isNew = server.isNew()
        return serverRepo.save(server).apply {
            if (isNew) {
                val serverId = this.id

                // Создание дефолтных каналов для нового сервера - текстового и голосового.
                channelService.saveChannel(Channel(serverId, DEFAULT_TEXT_CHANNEL_NAME, TEXT))
                channelService.saveChannel(Channel(serverId, DEFAULT_VOICE_CHANNEL_NAME, VOICE))

                // Создание дефолтной роли для нового сервера, которая будет присваиваться каждому нового участнику
                // навсегда.
                val defaultRoleId = roleService.saveRole(Role(serverId, DEFAULT_ROLE_NAME, true)).id

                // Создание участника для пользователя-создателя сервера с дефолтной ролью.
                val memberId = memberService.saveMember(Member(serverId = serverId, userId = this.ownerId)).id
                memberRoleService.saveRoleToMember(MemberRole(memberId = memberId, roleId = defaultRoleId))
            }
        }
    }

    override suspend fun findServer(id: UUID) = serverRepo.findById(id)
        ?: throw NotFoundException(SERVER_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findServerDefaultRole(serverId: UUID) =
        roleService.findDefaultServerRole(serverId)

    override suspend fun deleteServer(ownerId: UUID, id: UUID) =
        serverRepo.deleteByIdAndOwnerId(ownerId, id)

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