package tech.ruslo.gateway.api.server.ban.handler

import java.util.UUID
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.springframework.stereotype.Service
import tech.ruslo.domain.server.db.entity.Ban
import tech.ruslo.domain.server.usecase.ban.BanService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.domain.user.usecase.user.UserService
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.mapper.BanMapper
import tech.ruslo.websocket.dto.enums.SendEvents.SERVER_BAN_ADD
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о банах серверов.
 *
 * @property accessService сервис для работы с доступами.
 * @property banService сервис для работы с банами серверов.
 * @property userService сервис для работы с пользователями.
 * @property banMapper маппер для банов.
 * @property userWsStorage хранилище для 'WebSocket' соединений.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class BanApiServiceImpl(
    private val accessService: AccessService,
    private val banService: BanService,
    private val userService: UserService,
    private val banMapper: BanMapper,
    private val userWsStorage: UserWebSocketStorage,
) : BanApiService {
    override suspend fun findBans(serverId: UUID, before: UUID?, after: UUID?, limit: Int, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { banService.findBans(serverId, limit, before, after) }
            .map { banMapper.toDto(it) }

    override suspend fun searchBans(serverId: UUID, query: String, limit: Int, user: User) =
        accessService.isServerOwner(serverId, user.id)
            .let { banService.findBans(serverId) }
            .filter { userService.findUser(it.userId).username.contains(query) }
            .take(limit)
            .map { banMapper.toDto(it) }

    override suspend fun createBan(serverId: UUID, userId: UUID, reason: String?, user: User) {
        accessService.isServerOwner(serverId, user.id)
            .let { Ban(serverId, userId, reason) }
            .let { banService.createBan(it) }
            .let { banMapper.toDto(it) }
            .apply { userWsStorage.sendMessage(SERVER_BAN_ADD.name, this) }
    }

    override suspend fun removeBan(serverId: UUID, userId: UUID, user: User) {
        accessService.isServerOwner(serverId, user.id)
            .let { banService.removeBan(serverId, userId) }
        //.let { banMapper.toDto(it) }
        //.apply { userWsStorage.sendMessage(SERVER_BAN_REMOVE.name, this) }
    }
}