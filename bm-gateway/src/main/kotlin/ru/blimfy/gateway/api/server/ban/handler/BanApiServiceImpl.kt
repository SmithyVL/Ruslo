package ru.blimfy.gateway.api.server.ban.handler

import java.util.UUID
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.springframework.stereotype.Service
import ru.blimfy.gateway.api.mapper.BanMapper
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
 * @property serverService сервис для работы с серверами.
 * @property userService сервис для работы с пользователями.
 * @property banMapper маппер для банов.
 * @property userWsStorage хранилище для 'WebSocket' соединений.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class BanApiServiceImpl(
    private val banService: BanService,
    private val serverService: ServerService,
    private val userService: UserService,
    private val banMapper: BanMapper,
    private val userWsStorage: UserWebSocketStorage,
) : BanApiService {
    override suspend fun findBans(serverId: UUID, before: UUID?, after: UUID?, limit: Int, user: User) =
        serverService.checkServerWrite(serverId, user.id)
            .let {
                var end: Long
                var start: Long

                when {
                    before != null -> {
                        end = banService.findBan(before).position
                        start = end - limit
                    }

                    after != null -> {
                        start = banService.findBan(after).position
                        end = start + limit
                    }

                    else -> {
                        end = banService.getCountBans(serverId)
                        start = end - limit
                    }
                }

                banService.findBans(serverId, start, end)
            }
            .map { banMapper.toDto(it) }

    override suspend fun searchBans(serverId: UUID, query: String, limit: Int, user: User) =
        serverService.checkServerWrite(serverId, user.id)
            .let { banService.findBans(serverId) }
            .filter { userService.findUser(it.userId).username.contains(query) }
            .take(limit)
            .map { banMapper.toDto(it) }

    override suspend fun createBan(serverId: UUID, userId: UUID, reason: String?, user: User) {
        serverService.checkServerWrite(serverId, user.id)
            .let { banService.createBan(Ban(serverId, userId, reason)) }
            .let { banMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(SERVER_BAN_ADD, this) }
    }

    override suspend fun removeBan(serverId: UUID, userId: UUID, user: User) {
        serverService.checkServerWrite(serverId, user.id)
            .let { banService.removeBan(serverId, userId) }
            .let { banMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(SERVER_BAN_REMOVE, this) }
    }
}