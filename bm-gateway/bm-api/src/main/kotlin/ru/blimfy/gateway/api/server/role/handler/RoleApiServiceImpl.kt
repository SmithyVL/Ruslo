package ru.blimfy.gateway.api.server.role.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import ru.blimfy.domain.server.api.dto.role.ModifyRoleDto
import ru.blimfy.domain.server.api.dto.role.NewRoleDto
import ru.blimfy.domain.server.api.dto.role.RolePositionDto
import ru.blimfy.domain.server.usecase.member.MemberService
import ru.blimfy.domain.server.usecase.role.RoleService
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.access.control.service.AccessService
import ru.blimfy.gateway.dto.websockets.delete.RoleDeleteDto
import ru.blimfy.gateway.mapper.MemberMapper
import ru.blimfy.gateway.mapper.toDto
import ru.blimfy.websocket.dto.enums.SendEvents.SERVER_MEMBER_UPDATE
import ru.blimfy.websocket.dto.enums.SendEvents.SERVER_ROLE_CREATE
import ru.blimfy.websocket.dto.enums.SendEvents.SERVER_ROLE_DELETE
import ru.blimfy.websocket.dto.enums.SendEvents.SERVER_ROLE_UPDATE
import ru.blimfy.websocket.storage.UserWebSocketStorage

/**
 * Интерфейс для работы с обработкой запросов о ролях серверов.
 *
 * @property accessService сервис для работы с доступами.
 * @property roleService сервис для работы с участниками серверов.
 * @property memberService сервис для работы с участниками серверов.
 * @property memberMapper маппер для работы с участниками серверов.
 * @property wsStorage хранилище для WebSocket соединений.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class RoleApiServiceImpl(
    private val accessService: AccessService,
    private val roleService: RoleService,
    private val memberService: MemberService,
    private val memberMapper: MemberMapper,
    private val wsStorage: UserWebSocketStorage,
) : RoleApiService {
    override suspend fun createRole(serverId: UUID, newRoleDto: NewRoleDto, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.createRole(newRoleDto, serverId) }
            .toDto()
            .apply { wsStorage.sendMessage(SERVER_ROLE_CREATE.name, this) }

    override suspend fun findMemberIds(id: UUID, serverId: UUID, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { memberService.findRoleMembers(id, serverId) }
            .map { it.userId }

    override suspend fun modifyRole(id: UUID, serverId: UUID, modifyRoleDto: ModifyRoleDto, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.modifyRole(id, modifyRoleDto, serverId) }
            .toDto()
            .apply { wsStorage.sendMessage(SERVER_ROLE_UPDATE.name, this) }

    override suspend fun addRoleMembers(id: UUID, serverId: UUID, memberIds: List<UUID>, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.addMembers(id, memberIds, serverId) }
            .map { memberMapper.toDto(it) }
            .onEach { wsStorage.sendMessage(SERVER_MEMBER_UPDATE.name, it) }

    override suspend fun modifyPositions(
        serverId: UUID,
        positionDtos: List<RolePositionDto>,
        user: User,
    ) =
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.modifyPositions(serverId, positionDtos) }
            .map { it.toDto() }
            .onEach { wsStorage.sendMessage(SERVER_ROLE_UPDATE.name, it) }

    override suspend fun deleteRole(id: UUID, serverId: UUID, user: User) {
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.deleteRole(id, serverId) }
            .apply { wsStorage.sendMessage(SERVER_ROLE_DELETE.name, RoleDeleteDto(serverId, id)) }
    }
}