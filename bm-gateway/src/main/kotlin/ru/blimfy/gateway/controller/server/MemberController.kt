package ru.blimfy.gateway.controller.server

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.server.member.CurrentMemberNickDto
import ru.blimfy.gateway.dto.server.member.MemberNickDto
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.service.member.MemberControllerService

/**
 * Контроллер для работы с участниками серверов.
 *
 * @property memberControllerService сервис для обработки информации об участниках серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "MemberController", description = "Контроллер для работы с участниками серверов")
@RestController
@RequestMapping("/v1/members")
class MemberController(private val memberControllerService: MemberControllerService) {
    @Operation(summary = "Изменить ник участнику сервера")
    @PatchMapping
    suspend fun changeMemberNick(
        @RequestBody memberNick: MemberNickDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = memberControllerService.changeMemberNick(memberNick, userDetails.info)

    @Operation(summary = "Изменение ника участником сервера")
    @PatchMapping("/@me")
    suspend fun changeCurrentMemberNick(
        @RequestBody currentMemberNick: CurrentMemberNickDto,
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ) = memberControllerService.changeCurrentMemberNick(currentMemberNick, userDetails.info)
}