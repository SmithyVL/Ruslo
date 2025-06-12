package ru.blimfy.gateway.api.server.role.handler

import org.springframework.stereotype.Service
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.server.dto.role.ModifyRoleDto
import ru.blimfy.gateway.api.server.dto.role.NewRoleDto
import ru.blimfy.gateway.api.server.dto.role.toEntity
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.service.AccessService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.*
import java.util.*

/**
 * Интерфейс для работы с обработкой запросов о ролях серверов.
 *
 * @property accessService сервис для работы с доступами.
 * @property roleService сервис для работы с участниками серверов.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class RoleApiServiceImpl(
    private val accessService: AccessService,
    private val roleService: RoleService,
    private val userWsStorage: UserWebSocketStorage,
) : RoleApiService {
    override suspend fun createRole(serverId: UUID, newRoleDto: NewRoleDto, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.createRole(newRoleDto.toEntity(serverId)) }
            .toDto()
            .apply { userWsStorage.sendMessage(SERVER_ROLE_CREATE, this) }

    override suspend fun modifyRole(id: UUID, serverId: UUID, modifyRoleDto: ModifyRoleDto, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { roleService.findServerRole(id, serverId) }
            .let { roleService.modifyRole(modifyRoleDto.toEntity(it)) }
            .toDto()
            .apply { userWsStorage.sendMessage(SERVER_ROLE_UPDATE, this) }
}