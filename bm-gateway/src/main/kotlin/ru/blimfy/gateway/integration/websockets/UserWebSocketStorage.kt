package ru.blimfy.gateway.integration.websockets

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import ru.blimfy.direct.usecase.conservation.member.MemberConservationService
import ru.blimfy.security.service.TokenService
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.websocket.dto.WsMessageTypes
import ru.blimfy.websocket.storage.WebSocketStorage

/**
 * Класс-хранилище WebSocket сессий для пользователей.
 *
 * @param objectMapper маппер для сериализации/десериализации объектов.
 * @property memberService сервис для работы с участниками серверов.
 * @property memberConservationService сервис для работы с участиями пользователей в личных диалогах.
 * @property tokenService сервис для работы с JWT токенами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class UserWebSocketStorage(
    objectMapper: ObjectMapper,
    private val memberService: MemberService,
    private val memberConservationService: MemberConservationService,
    private val tokenService: TokenService,
) : WebSocketStorage<UUID>(objectMapper) {
    /**
     * Отправляет уведомление с [data] об изменении [type] для сервера с [serverId] через Websocket'ы кроме
     * [skipUserId].
     */
    fun sendServerMessages(serverId: UUID, type: WsMessageTypes, data: Any, skipUserId: UUID) =
        CoroutineScope(IO).launch {
            memberService.findServerMembers(serverId)
                .filter { it.userId != skipUserId }
                .onEach { sendMessage(it.userId, type, data) }
                .collect()
        }

    /**
     * Отправляет уведомление об изменении [type] для личного диалога с [conservationId] через Websocket'ы кроме
     * [skipUserId].
     */
    fun sendConservationMessages(conservationId: UUID, type: WsMessageTypes, data: Any, skipUserId: UUID) =
        CoroutineScope(IO).launch {
            memberConservationService.findConservationMembers(conservationId)
                .filter { it.userId != skipUserId }
                .onEach { sendMessage(it.userId, type, data) }
                .collect()
        }

    override fun addSession(token: String, session: WebSocketSession) {
        sessions[tokenService.extractUserId(token)] = session
    }

    override fun removeSession(userId: UUID) {
        sessions.remove(userId)
    }
}