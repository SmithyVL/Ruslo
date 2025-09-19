package tech.ruslo.gateway.api.server.member.handler

import java.util.UUID
import org.springframework.stereotype.Service
import tech.ruslo.domain.server.usecase.member.MemberService
import tech.ruslo.domain.server.usecase.role.RoleService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.mapper.MemberMapper
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_MEMBER_REMOVE
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_MEMBER_UPDATE
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @property accessService сервис для работы с доступами.
 * @property memberService сервис для работы с участниками серверов.
 * @property roleService сервис для работы с ролями.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberApiServiceImpl(
    private val accessService: AccessService,
    private val memberService: MemberService,
    private val roleService: RoleService,
    private val memberMapper: MemberMapper,
    private val userWsStorage: UserWebSocketStorage,
) : MemberApiService {
    override suspend fun removeMember(serverId: UUID, userId: UUID, user: User) {
        accessService.isServerOwner(serverId, user.id)
            .let { memberService.deleteUserMember(userId, serverId) }
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_REMOVE.name, userId) }
    }

    override suspend fun changeMemberNick(serverId: UUID, userId: UUID, nick: String?, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { memberService.setNick(serverId, userId, nick) }
            .let { memberMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE.name, this) }

    override suspend fun changeMemberRoles(serverId: UUID, userId: UUID, roleIds: List<UUID>, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.changeMemberRoles(roleIds, serverId, userId) }
            .let { memberMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE.name, this) }

    override suspend fun changeCurrentMemberNick(serverId: UUID, nick: String?, user: User) =
        user.id.let { userId ->
            accessService.isServerMember(serverId, userId)
                .let { memberService.setNick(serverId, userId, nick) }
                .let { memberMapper.toDto(it) }
                .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE.name, this) }
        }
}