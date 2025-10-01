package tech.ruslo.gateway.controller.server

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.server.dto.ServerDto

/**
 * REST API контроллер для работы с серверами.
 *
 * @property serverHandler обработчик для обработки информации о серверах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ServerController", description = "REST API контроллер для работы с серверами пользователей")
@RestController
@RequestMapping("/servers")
class ServerController(private val serverHandler: ServerHandler) {
    @Operation(summary = "Сохранить сервер")
    @PostMapping
    suspend fun saveServer(@RequestBody serverDto: ServerDto) = serverHandler.saveServer(serverDto)
}