package ru.blimfy.gateway.api.server.ban.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.springframework.stereotype.Service
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.server.dto.ban.BanDto
import ru.blimfy.gateway.api.server.dto.ban.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.db.entity.Ban
import ru.blimfy.server.usecase.ban.BanService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_BAN_ADD
import ru.blimfy.websocket.dto.WsMessageTypes.SERVER_BAN_REMOVE

/**
 * Реализация интерфейса для работы с обработкой запросов о банах серверов.
 *
 * @property banService сервис для работы с банами серверов.
 * @property userService сервис для работы с пользователями.
 * @property userWsStorage хранилище для 'WebSocket' соединений.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class BanApiServiceImpl(
    private val banService: BanService,
    private val serverService: ServerService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : BanApiService {
    override suspend fun findBans(serverId: UUID, before: UUID?, after: UUID?, limit: Int, user: User): Flow<BanDto> {
        serverService.checkServerWrite(serverId = serverId, userId = user.id)

        return when {
            before != null -> {
                val end = banService.findBan(before).position
                val start = end - limit
                banService.findBans(serverId, start, end)
            }

            after != null -> {
                val start = banService.findBan(after).position
                val end = start + limit
                banService.findBans(serverId, start, end)
            }

            else -> {
                val end = banService.getCountBans(serverId)
                val start = end - limit
                banService.findBans(serverId, start, end)
            }
        }.map { it.toDto().apply { this.user = userService.findUser(it.userId).toDto() } }
    }

    override suspend fun searchBans(serverId: UUID, query: String, limit: Int, user: User): Flow<BanDto> {
        serverService.checkServerWrite(serverId = serverId, userId = user.id)
        return banService.findBans(serverId)
            .filter { ban -> userService.findUser(ban.userId).username.contains(query) }
            .take(limit)
            .map { it.toDto().apply { this.user = userService.findUser(it.userId).toDto() } }
    }

    override suspend fun createBan(serverId: UUID, userId: UUID, reason: String?, user: User) {
        serverService.checkServerWrite(serverId = serverId, userId = user.id)
        banService.createBan(Ban(serverId, userId, reason))
            .let { it.toDto().apply { this.user = userService.findUser(it.userId).toDto() } }
            .apply { userWsStorage.sendMessage(SERVER_BAN_ADD, this) }
    }

    override suspend fun removeBan(serverId: UUID, userId: UUID, user: User) {
        serverService.checkServerWrite(serverId = serverId, userId = user.id)
        banService.removeBan(serverId, userId)
            .let { it.toDto().apply { this.user = userService.findUser(it.userId).toDto() } }
            .apply { userWsStorage.sendMessage(SERVER_BAN_REMOVE, this) }
    }
}