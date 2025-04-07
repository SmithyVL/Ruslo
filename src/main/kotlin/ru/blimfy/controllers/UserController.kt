package ru.blimfy.controllers

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
import ru.blimfy.common.dto.ConservationDto
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.repository.MemberConservationRepository
import ru.blimfy.security.TokenService
import ru.blimfy.services.conservation.ConservationService
import ru.blimfy.services.member.MemberService
import ru.blimfy.services.server.ServerService
import ru.blimfy.services.user.UserService

/**
 * REST API контроллер для работы с пользователями.
 *
 * @property userService сервис для работы с пользователями.
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property memberConservationRepo репозиторий для работы с участниками личных диалогов в БД.
 * @property conservationService сервис для работы с личными сообщениями.
 * @property tokenService сервис для работы с токенами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "UserController", description = "REST API для работы с информацией пользователей")
@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService,
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val memberConservationRepo: MemberConservationRepository,
    private val conservationService: ConservationService,
    private val tokenService: TokenService,
) {
    @Operation(summary = "Получить все сервера пользователя")
    @GetMapping("/servers")
    suspend fun findUserServers(principal: Principal) =
        memberService.findUserMembers(tokenService.extractUserId(principal))
            .map { serverService.findServer(it.serverId) }
            .map { it.toDto() }

    @Operation(summary = "Получить все личные диалоги пользователя")
    @GetMapping("/conservations")
    suspend fun findUserConservations(principal: Principal) =
        memberConservationRepo.findAllByUserId(tokenService.extractUserId(principal))
            .map {
                val user = userService.findUser(it.userId)
                ConservationDto(it.conservationId, user.username, user.avatarUrl)
            }

    @Operation(summary = "Покинуть сервер пользователем")
    @DeleteMapping("/servers/{serverId}")
    suspend fun leaveServer(@PathVariable serverId: UUID, principal: Principal) =
        memberService.deleteServerMember(tokenService.extractUserId(principal), serverId)
}