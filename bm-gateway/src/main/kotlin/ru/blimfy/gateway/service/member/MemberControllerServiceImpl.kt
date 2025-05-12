package ru.blimfy.gateway.service.member

import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.member.MemberDto
import ru.blimfy.gateway.dto.member.ModifyMemberDto
import ru.blimfy.gateway.dto.member.toDto
import ru.blimfy.gateway.dto.member.toEntity
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_SERVER_MEMBER

/**
 * Интерфейс для работы с обработкой запросов об участниках серверов.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property serverService сервис для работы с серверами.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberControllerServiceImpl(
    private val memberService: MemberService,
    private val serverService: ServerService,
    private val userWebSocketStorage: UserWebSocketStorage,
) : MemberControllerService {
    override suspend fun modifyMember(modifyMemberDto: ModifyMemberDto, user: CustomUserDetails): MemberDto {
        val userId = user.userInfo.id

        // Изменить участника на сервере может только его создатель.
        serverService.checkServerModifyAccess(serverId = modifyMemberDto.serverId, userId = userId)

        return memberService.saveMember(modifyMemberDto.toEntity()).toDto()
            .apply { userWebSocketStorage.sendServerMessages(serverId, EDIT_SERVER_MEMBER, this, userId) }
    }
}