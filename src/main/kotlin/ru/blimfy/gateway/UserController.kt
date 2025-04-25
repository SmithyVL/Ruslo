package ru.blimfy.gateway

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.direct.conservation.ConservationTitleDto
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.repository.MemberConservationRepository
import ru.blimfy.security.service.TokenService
import ru.blimfy.services.member.MemberService
import ru.blimfy.services.server.ServerService
import ru.blimfy.services.user.UserService
import ru.blimfy.websockets.UserWebSocketService
import ru.blimfy.websockets.dto.WsMessageTypes.REMOVE_SERVER_MEMBER

/**
 * Контроллер для работы с пользователями.
 *
 * @property userService сервис для работы с пользователями.
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property memberConservationRepo репозиторий для работы с участниками личных диалогов в БД.
 * @property tokenService сервис для работы с токенами.
 * @property userWebSocketService сервис для работы с отправкой данных через WebSocket соединения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "UserController", description = "Контроллер для работы с информацией пользователей")
@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService,
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val memberConservationRepo: MemberConservationRepository,
    private val tokenService: TokenService,
    private val userWebSocketService: UserWebSocketService,
) {
    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping
    suspend fun getCurrentUser(principal: Principal) =
        tokenService.extractUserId(principal).let { userId ->
            userService.findUser(userId).toDto()
        }

    @Operation(summary = "Получить все сервера пользователя")
    @GetMapping("/servers")
    suspend fun findUserServers(principal: Principal) =
        tokenService.extractUserId(principal).let { userId ->
            memberService.findUserMembers(userId)
                .map { serverService.findServer(it.serverId) }
                .map { it.toDto() }
        }

    @Operation(summary = "Получить все личные диалоги пользователя")
    @GetMapping("/conservations")
    suspend fun findUserConservations(principal: Principal) =
        tokenService.extractUserId(principal).let { userId ->
            memberConservationRepo.findAllByUserId(userId)
                .map {
                    val user = userService.findUser(it.userId)
                    ConservationTitleDto(it.conservationId, user.username, user.avatarUrl)
                }
        }

    @Operation(summary = "Покинуть сервер пользователем")
    @DeleteMapping("/servers/{serverId}")
    suspend fun leaveServer(@PathVariable serverId: UUID, principal: Principal) {
        val userId = tokenService.extractUserId(principal)

        memberService.deleteUserMember(userId = userId, serverId = serverId).apply {
            userWebSocketService.sendServerMessages(serverId, REMOVE_SERVER_MEMBER, userId, userId)
        }
    }
}