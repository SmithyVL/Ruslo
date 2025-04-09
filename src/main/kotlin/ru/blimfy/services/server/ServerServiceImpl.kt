package ru.blimfy.services.server

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.exception.Errors.SERVER_BY_ID_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.Channel
import ru.blimfy.persistence.entity.Member
import ru.blimfy.persistence.entity.MemberRole
import ru.blimfy.persistence.entity.Role
import ru.blimfy.persistence.entity.Server
import ru.blimfy.persistence.repository.ChannelRepository
import ru.blimfy.persistence.repository.MemberRepository
import ru.blimfy.persistence.repository.MemberRoleRepository
import ru.blimfy.persistence.repository.RoleRepository
import ru.blimfy.persistence.repository.ServerRepository
import ru.blimfy.services.role.RoleServiceImpl.Companion.ROLE_FOR_ALL_NAME

/**
 * Реализация интерфейса для работы с серверами пользователя.
 *
 * @property serverRepo репозиторий для работы с серверами в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerServiceImpl(
    private val serverRepo: ServerRepository,
    private val channelRepo: ChannelRepository,
    private val memberRepo: MemberRepository,
    private val roleRepo: RoleRepository,
    private val memberRolesRepo: MemberRoleRepository,
) : ServerService {
    @Transactional
    override suspend fun saveServer(server: Server): Server {
        val isNew = server.isNew()
        return serverRepo.save(server).apply {
            if (isNew) {
                val serverId = this.id

                channelRepo.save(Channel(this.id, CHANNEL_DEFAULT_NAME))

                val memberId = memberRepo.save(Member(serverId, this.ownerId)).id
                roleRepo.save(Role(serverId, ROLE_FOR_ALL_NAME)).apply {
                    memberRolesRepo.save(MemberRole(memberId, this.id))
                }
            }
        }
    }

    override suspend fun findServer(id: UUID) = serverRepo.findById(id)
        ?: throw NotFoundException(SERVER_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun deleteServer(ownerId: UUID, id: UUID) =
        serverRepo.deleteByIdAndOwnerId(ownerId, id)

    private companion object {
        /**
         * Стандартное название канала.
         */
        const val CHANNEL_DEFAULT_NAME = "Основной"
    }
}