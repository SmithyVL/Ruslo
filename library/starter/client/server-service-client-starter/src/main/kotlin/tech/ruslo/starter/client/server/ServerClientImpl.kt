package tech.ruslo.starter.client.server

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToFlow
import tech.ruslo.server.dto.ServerClient
import tech.ruslo.server.dto.ServerDto

/**
 * Реализация клиентского интерфейса для работы с информацией о серверах из сервиса серверов.
 *
 * @property serverWebClient настроенный клиент для запросов в сервис серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerClientImpl(val serverWebClient: WebClient) : ServerClient {
    override suspend fun saveServer(serverDto: ServerDto) = serverWebClient
        .post()
        .uri(SERVERS_PATH_PREFIX)
        .bodyValue(serverDto)
        .retrieve()
        .awaitBody<ServerDto>()

    override fun getUserServers(userId: Long) = serverWebClient
        .get()
        .uri { it.path(SERVERS_PATH_PREFIX).queryParam("userId", userId).build() }
        .retrieve()
        .bodyToFlow<ServerDto>()

    private companion object {
        /**
         * Префикс для запросов о серверах.
         */
        private const val SERVERS_PATH_PREFIX = "/servers"
    }
}