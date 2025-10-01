package tech.ruslo.gateway.controller.server

import org.springframework.stereotype.Service
import tech.ruslo.server.dto.ServerClient
import tech.ruslo.server.dto.ServerDto

/**
 * Обработчик для работы с обработкой запросов о серверах.
 *
 * @property serverClient клиент для работы с сервисом сервером.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ServerHandler(private val serverClient: ServerClient) {
    suspend fun saveServer(serverDto: ServerDto) =
        serverClient.saveServer(serverDto)
}