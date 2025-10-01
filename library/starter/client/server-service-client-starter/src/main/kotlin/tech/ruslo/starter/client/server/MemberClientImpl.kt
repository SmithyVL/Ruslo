package tech.ruslo.starter.client.server

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import tech.ruslo.server.dto.member.MemberClient

/**
 * Реализация клиентского интерфейса для работы с информацией об участниках серверов из сервиса серверов.
 *
 * @property serverWebClient настроенный клиент для запросов в сервис серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberClientImpl(val serverWebClient: WebClient) : MemberClient {
    override suspend fun removeMember(userId: Long, serverId: Long) = serverWebClient
        .delete()
        .uri("$MEMBERS_PATH_PREFIX/{userId}", serverId, userId)
        .retrieve()
        .awaitBody<Unit>()

    private companion object {
        /**
         * Префикс для запросов об участниках серверов.
         */
        private const val MEMBERS_PATH_PREFIX = "/servers/{serverId}/members"
    }
}