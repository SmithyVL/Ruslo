package ru.blimfy.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import java.util.UUID
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.common.dto.InviteDto
import ru.blimfy.persistence.entity.Member
import ru.blimfy.persistence.entity.MemberRole
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.security.TokenService
import ru.blimfy.services.invite.InviteService
import ru.blimfy.services.member.MemberService
import ru.blimfy.services.member.role.MemberRoleService
import ru.blimfy.services.server.ServerService

/**
 * REST API контроллер для работы с приглашениями на сервера.
 *
 * @property inviteService сервис для работы с приглашениями на сервера.
 * @property serverService сервис для работы с серверами.
 * @property memberService сервис для работы с участниками серверов.
 * @property memberRoleService сервис для работы с ролями участников серверов.
 * @property tokenService сервис для работы с токенами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "InviteController", description = "REST API для работы с приглашениями на сервера")
@RestController
@RequestMapping("/v1/invites")
class InviteController(
    private val inviteService: InviteService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val memberRoleService: MemberRoleService,
    private val tokenService: TokenService,
) {
    @Operation(summary = "Создать/Обновить приглашение на сервер")
    @PostMapping
    suspend fun saveInvite(@RequestBody inviteDto: InviteDto) =
        inviteService.saveInvite(inviteDto.toEntity()).toDto()

    @Operation(summary = "Использовать приглашение на сервер")
    @PostMapping("/{inviteId}")
    @Transactional
    suspend fun useInvite(@PathVariable inviteId: UUID, principal: Principal) =
        inviteService.findInvite(inviteId).serverId.apply {
            val userId = tokenService.extractUserId(principal)
            val defaultRoleId = serverService.findServerDefaultRole(this).id
            val memberId = memberService.saveMember(Member(serverId = this, userId = userId)).id
            memberRoleService.saveRoleToMember(MemberRole(memberId = memberId, roleId = defaultRoleId))
        }

    @Operation(summary = "Удалить приглашение на сервер")
    @DeleteMapping("/{inviteId}")
    suspend fun deleteInvite(@PathVariable inviteId: UUID) = inviteService.deleteInvite(inviteId)
}