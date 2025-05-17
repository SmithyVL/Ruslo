package ru.blimfy.gateway.service.member

import java.util.UUID
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.server.member.CurrentMemberNickDto
import ru.blimfy.gateway.dto.server.member.MemberDto
import ru.blimfy.gateway.dto.server.member.MemberNickDto
import ru.blimfy.gateway.dto.server.member.toDto
import ru.blimfy.gateway.dto.server.role.toDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.GUILD_MEMBER_UPDATE

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @property userService сервис для работы с пользователями.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberControllerServiceImpl(
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val roleService: RoleService,
    private val memberRoleService: MemberRoleService,
    private val userService: UserService,
    private val userWebSocketStorage: UserWebSocketStorage,
) : MemberControllerService {
    override suspend fun changeMemberNick(memberNick: MemberNickDto, currentUser: User): MemberDto {
        val currentUserId = currentUser.id
        val serverId = memberNick.serverId

        // Изменить ник участника на сервере может только его создатель.
        serverService.checkServerModifyAccess(serverId = serverId, userId = currentUserId)

        return changeNick(serverId, memberNick.userId, memberNick.nick)
            .apply { userWebSocketStorage.sendServerMessages(serverId, GUILD_MEMBER_UPDATE, this, currentUserId) }
    }

    override suspend fun changeCurrentMemberNick(memberNick: CurrentMemberNickDto, currentUser: User): MemberDto {
        val currentUserId = currentUser.id
        val serverId = memberNick.serverId

        // Изменить свой ник на сервере может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = currentUserId)

        return changeNick(serverId, currentUserId, memberNick.nick)
            .apply { userWebSocketStorage.sendServerMessages(serverId, GUILD_MEMBER_UPDATE, this, currentUserId) }
    }

    /**
     * Возвращает обновлённого участника с [userId] и [newNick] для [serverId].
     */
    private suspend fun changeNick(serverId: UUID, userId: UUID, newNick: String? = null) =
        memberService.setNick(serverId, userId, newNick)
            .toDto()
            .apply {
                user = userService.findUser(userId).toDto()
                roles = memberRoleService.findMemberRoles(id)
                    .map { roleService.findRole(it.roleId) }
                    .map { it.toDto() }
                    .toList()
            }
}