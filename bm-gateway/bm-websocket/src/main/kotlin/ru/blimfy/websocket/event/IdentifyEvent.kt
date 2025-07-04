package ru.blimfy.websocket.event

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import ru.blimfy.domain.server.usecase.server.ServerService
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.mapper.ServerMapper
import ru.blimfy.gateway.mapper.toDto
import ru.blimfy.websocket.dto.event.read.IdentifyDto
import ru.blimfy.websocket.dto.event.send.ReadyDto
import ru.blimfy.websocket.dto.enums.ReceiveEvents.IDENTIFY
import ru.blimfy.websocket.dto.event.read.ReadEvent
import ru.blimfy.websocket.event.base.BaseEvent
import ru.blimfy.websocket.service.WsTokenService
import ru.blimfy.websocket.storage.UserWebSocketStorage

/**
 * Обработчик идентифицирующего события.
 *
 * @property wsStorage хранилище WebSocket сессий.
 * @property wsTokenService сервис для работы с токенами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class IdentifyEvent(
    private val wsStorage: UserWebSocketStorage,
    private val wsTokenService: WsTokenService,
    private val userService: UserService,
    private val serverService: ServerService,
    private val serverMapper: ServerMapper,
) : BaseEvent {
    override val type = IDENTIFY

    override suspend fun process(session: WebSocketSession, data: ReadEvent) {
        val payload = data as IdentifyDto
        val userId = wsTokenService.extractUserId(payload.token)
        wsStorage.addSession(userId, session)

        val user = userService.findUser(userId).toDto()
        val servers = serverService.findUserServers(userId)
            .map { serverMapper.toDto(it) }
            .toList()
        wsStorage.sendMessage(session, ReadyDto(user, servers))
    }
}