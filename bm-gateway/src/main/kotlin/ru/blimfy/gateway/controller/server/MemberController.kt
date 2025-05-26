package ru.blimfy.gateway.controller.server

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.common.NewNickDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.member.MemberControllerService

/**
 * Контроллер для работы с участниками серверов.
 *
 * @property service сервис для обработки информации об участниках серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "MemberController", description = "Контроллер для работы с участниками серверов")
@RestController
@RequestMapping("/v1/servers/{serverId}/members")
class MemberController(private val service: MemberControllerService) {
    @Operation(summary = "Получить участников сервера")
    @GetMapping
    suspend fun findMembers(
        @PathVariable serverId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.findMembers(serverId, userDetails.info)

    @Operation(summary = "Удалить участника сервера")
    @DeleteMapping("/{userId}")
    suspend fun removeMember(
        @PathVariable serverId: UUID,
        @PathVariable userId: UUID,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.removeMember(serverId, userId, userDetails.info)

    @Operation(summary = "Изменить ник участнику сервера")
    @PutMapping("/{userId}/nick")
    suspend fun changeMemberNick(
        @PathVariable serverId: UUID,
        @PathVariable userId: UUID,
        @RequestBody nickInfo: NewNickDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changeMemberNick(serverId, userId, nickInfo.nick, userDetails.info)

    @Operation(summary = "Изменение ника участником сервера")
    @PutMapping("/@me/nick")
    suspend fun changeCurrentMemberNick(
        @PathVariable serverId: UUID,
        @RequestBody nickInfo: NewNickDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = service.changeCurrentMemberNick(serverId, nickInfo.nick, userDetails.info)
}