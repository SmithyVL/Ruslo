package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.enumeration.PrivilegeTypes
import ru.blimfy.gateway.dto.member.MemberDetailsDto
import ru.blimfy.gateway.dto.member.MemberDto
import ru.blimfy.gateway.dto.member.toDetailsDto
import ru.blimfy.gateway.dto.member.toDto
import ru.blimfy.gateway.dto.member.toEntity
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.member.role.MemberRoleService
import ru.blimfy.server.usecase.privilege.PrivilegeService
import ru.blimfy.server.usecase.role.RoleService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_SERVER_MEMBER

/**
 * Контроллер для работы с участниками серверов.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property roleService сервис для работы с ролями сервера.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @property tokenService сервис для работы с токенами.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "MemberController", description = "Контроллер для работы с участниками серверов")
@RestController
@RequestMapping("/v1/members")
class MemberController(
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val roleService: RoleService,
    private val memberRoleService: MemberRoleService,
    private val tokenService: TokenService,
    private val userWebSocketStorage: UserWebSocketStorage,
    private val privilegeService: PrivilegeService,
) {
    @Operation(summary = "Обновить информацию об участнике сервера")
    @PutMapping
    suspend fun modifyMember(@RequestBody memberDto: MemberDto, principal: Principal): MemberDto {
        val userId = tokenService.extractUserId(principal)

        // Изменить имя участника на сервере может только его создатель.
        serverService.checkServerModifyAccess(serverId = memberDto.serverId, userId = userId)

        return memberService.saveMember(memberDto.toEntity()).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, EDIT_SERVER_MEMBER, this, userId) }
    }

    @Operation(summary = "Получить информацию о профиле участника сервера")
    @GetMapping("/{memberId}")
    suspend fun findMember(@PathVariable memberId: UUID, principal: Principal): MemberDetailsDto {
        val userId = tokenService.extractUserId(principal)
        val member = memberService.findMember(memberId)
            .apply {
                // Получить участника сервера может только участник этого сервера.
                serverService.checkServerViewAccess(serverId = serverId, userId = userId)
            }

        return member.toDetailsDto().apply {
            roles = memberRoleService.findMemberRoles(memberId)
                .map { roleService.findRole(it.roleId) }
                .filter { !it.basic }
                .map { it.name }
                .toList()
        }
    }

    @Operation(summary = "Получить привилегии участника сервера")
    @GetMapping("/{memberId}/privileges")
    suspend fun findMemberPrivileges(@PathVariable memberId: UUID, principal: Principal): Flow<PrivilegeTypes> {
        val userId = tokenService.extractUserId(principal)
        val serverId = memberService.findMember(memberId).serverId

        // Получить привилегии участника сервера может только участник этого сервера.
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        val roleId = memberRoleService.findMemberRoles(memberId).first().roleId
        return privilegeService.findRolePrivileges(roleId)
            .filter { it.isGranted }
            .map { it.type }
    }
}