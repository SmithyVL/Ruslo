package tech.ruslo.websocket.event

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import tech.ruslo.domain.server.usecase.server.ServerService
import tech.ruslo.domain.user.usecase.user.UserService
import tech.ruslo.gateway.mapper.ServerMapper
import tech.ruslo.gateway.mapper.toDto
import tech.ruslo.websocket.dto.event.read.IdentifyDto
import tech.ruslo.websocket.dto.event.send.ReadyDto
import tech.ruslo.websocket.dto.enums.ReceiveEvents.IDENTIFY
import tech.ruslo.websocket.dto.event.read.ReadEvent
import tech.ruslo.websocket.event.base.BaseEvent
import tech.ruslo.websocket.service.WsTokenService
import tech.ruslo.websocket.storage.UserWebSocketStorage

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