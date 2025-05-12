package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.member.ModifyMemberDto
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
    @Operation(summary = "Обновить информацию об участнике сервера")
    @PutMapping
    suspend fun modifyMember(
        @RequestBody modifyMemberDto: ModifyMemberDto,
        @AuthenticationPrincipal user: CustomUserDetails,
    ) = memberControllerService.modifyMember(modifyMemberDto, user)
}