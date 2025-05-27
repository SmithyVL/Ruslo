package ru.blimfy.gateway.service.member

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.server.member.MemberDto
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
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_REMOVE
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_MEMBER_UPDATE

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property roleService сервис для работы с ролями серверов.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @property userService сервис для работы с пользователями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberApiServiceImpl(
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val roleService: RoleService,
    private val memberRoleService: MemberRoleService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : MemberApiService {
    override suspend fun findMembers(serverId: UUID, user: User): Flow<MemberDto> {
        // Получить участников сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = user.id)

        return memberService.findServerMembers(serverId)
            .map { member ->
                member.toDto()
                    .apply {
                        roles = memberRoleService.findMemberRoles(serverId)
                            .map { roleService.findRole(it.roleId) }
                            .map { it.toDto() }
                            .toList()

                        this.user = userService.findUser(member.userId).toDto()
                    }
            }
    }

    override suspend fun removeMember(serverId: UUID, userId: UUID, user: User) {
        // Удалить участника сервера может только его создатель.
        serverService.checkServerModifyAccess(serverId = serverId, userId = user.id)

        memberService.deleteUserMember(userId = userId, serverId = serverId)
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_REMOVE, userId) }
    }

    override suspend fun changeMemberNick(serverId: UUID, userId: UUID, nick: String?, user: User): MemberDto {
        // Изменить ник участника на сервере может только его создатель.
        serverService.checkServerModifyAccess(serverId = serverId, userId = user.id)

        return changeNick(serverId, userId, nick)
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE, this) }
    }

    override suspend fun changeCurrentMemberNick(serverId: UUID, nick: String?, user: User): MemberDto {
        val currentUserId = user.id

        // Изменить свой ник на сервере может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = currentUserId)

        return changeNick(serverId, currentUserId, nick)
            .apply { userWsStorage.sendMessage(SERVER_MEMBER_UPDATE, this) }
    }

    /**
     * Возвращает обновлённого участника с [userId] и новым [nick] для [serverId].
     */
    private suspend fun changeNick(serverId: UUID, userId: UUID, nick: String? = null) =
        memberService.setNick(serverId, userId, nick)
            .toDto()
            .apply {
                user = userService.findUser(userId).toDto()
                roles = memberRoleService.findMemberRoles(id)
                    .map { roleService.findRole(it.roleId) }
                    .map { it.toDto() }
                    .toList()
            }
}