package tech.ruslo.server.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.server.dto.member.MemberClient
import tech.ruslo.server.service.member.MemberService

/**
 * REST API контроллер для работы с участниками серверов.
 *
 * @property memberService сервис для обработки информации об участниках серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "MemberController", description = "REST API контроллер для работы с участниками серверов")
@RestController
@RequestMapping("/servers/{serverId}/members")
class MemberController(private val memberService: MemberService) : MemberClient {
    @Operation(summary = "Удалить участника сервера")
    @DeleteMapping("/{userId}")
    override suspend fun removeMember(@PathVariable userId: Long, @PathVariable serverId: Long) =
        memberService.removeMember(userId, serverId)
}