package ru.blimfy.websockets

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import ru.blimfy.services.conservation.member.MemberConservationService
import ru.blimfy.services.member.MemberService
import ru.blimfy.websockets.dto.WsMessage
import ru.blimfy.websockets.dto.WsMessageTypes

/**
 * Сервис для работы с пользовательскими WebSocket сессиями.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @property memberConservationService сервис для работы с участиями пользователей в личных диалогах.
 * @property objectMapper маппер для сериализации/десериализации объектов.
 * @property userSessions пользовательские WebSocket сессии.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class UserWebSocketService(
    private val memberService: MemberService,
    private val memberConservationService: MemberConservationService,
    private val objectMapper: ObjectMapper,
) {
    private val userSessions = ConcurrentHashMap<UUID, WebSocketSession>()

    /**
     * Добавляет новую WebSocket [userSession] для пользователя с [userId].
     */
    fun addUserSession(userId: UUID, userSession: WebSocketSession) {
        userSessions[userId] = userSession
    }

    /**
     * Удаляет существующую WebSocket сессию для пользователя с [userId].
     */
    fun removeUserSession(userId: UUID) {
        userSessions.remove(userId)
    }

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

    /**
     * Отправляет [type] сообщение с [data] пользователю с [userId].
     */
    private suspend fun sendMessage(userId: UUID, type: WsMessageTypes, data: Any) {
        userSessions.get(userId)?.let { userSession ->
            userSession
                .send(Mono.just(userSession.textMessage(objectMapper.writeValueAsString(WsMessage(type, data)))))
                .subscribe()
        }
    }
}