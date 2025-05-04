package ru.blimfy.gateway.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.security.Principal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.blimfy.gateway.dto.MemberDto
import ru.blimfy.gateway.dto.toDto
import ru.blimfy.gateway.dto.toEntity
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_SERVER_MEMBER

/**
 * Контроллер для работы с участниками серверов.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
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
    private val tokenService: TokenService,
    private val userWebSocketStorage: UserWebSocketStorage,
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
}