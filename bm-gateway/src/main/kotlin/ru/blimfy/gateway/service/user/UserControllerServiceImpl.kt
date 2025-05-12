package ru.blimfy.gateway.service.user

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.direct.usecase.conservation.member.MemberConservationService
import ru.blimfy.gateway.dto.conservation.ConservationTitleDto
import ru.blimfy.gateway.dto.server.toDto
import ru.blimfy.gateway.dto.user.ModifyUserDto
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.dto.user.toEntity
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_SERVER_MEMBER

/**
 * Реализация интерфейса для работы с обработкой запросов о пользователях.
 *
 * @property userService сервис для работы с пользователями.
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property memberConservationService сервис для работы с участниками личных диалогов в БД.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserControllerServiceImpl(
    private val userService: UserService,
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val memberConservationService: MemberConservationService,
    private val userWebSocketStorage: UserWebSocketStorage,
) : UserControllerService {
    override suspend fun modifyUser(modifyUser: ModifyUserDto, user: CustomUserDetails) =
        user.userInfo
            .let { userService.modifyUser(modifyUser.toEntity(it.id, it.username, it.verified, it.createdDate)) }
            .toDto()

    override fun findUserServers(user: CustomUserDetails) =
        memberService.findUserMembers(user.userInfo.id)
            .map { serverService.findServer(it.serverId) }
            .map { it.toDto() }

    override fun findUserConservations(user: CustomUserDetails) =
        memberConservationService.findMemberConservations(user.userInfo.id)
            .map {
                val user = userService.findUser(it.userId)
                ConservationTitleDto(it.conservationId, user.username, user.avatar)
            }

    override suspend fun leaveServer(serverId: UUID, user: CustomUserDetails) {
        val userId = user.userInfo.id

        memberService.deleteUserMember(userId = userId, serverId = serverId).apply {
            userWebSocketStorage.sendServerMessages(serverId, REMOVE_SERVER_MEMBER, userId, userId)
        }
    }
}