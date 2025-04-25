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
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
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
    private val userSessions = ConcurrentHashMap<WebSocketSession, UUID>()

    /**
     * Добавляет новую WebSocket [userSession] для пользователя с [userId].
     */
    fun addUserSession(userId: UUID, userSession: WebSocketSession) {
        userSessions[userSession] = userId
    }

    /**
     * Удаляет существующую WebSocket [userSession].
     */
    fun removeUserSession(userSession: WebSocketSession) {
        userSessions.remove(userSession)
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
    private suspend fun sendMessage(userId: UUID, type: WsMessageTypes, data: Any) =
        userSessions.forEach {
            if (it.value == userId) {
                val userSession = it.key
                val wsMessage = mono { userSession.textMessage(objectMapper.writeValueAsString(WsMessage(type, data))) }
                userSession.send(wsMessage).subscribe()
            }
        }
}