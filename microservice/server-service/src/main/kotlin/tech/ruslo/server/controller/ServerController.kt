package tech.ruslo.server.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.map
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tech.ruslo.server.dto.ServerClient
import tech.ruslo.server.dto.ServerDto
import tech.ruslo.server.mapper.toDto
import tech.ruslo.server.mapper.toEntity
import tech.ruslo.server.service.server.ServerService

/**
 * REST API контроллер для работы с серверами.
 *
 * @property serverService сервис для обработки информации о серверах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Tag(name = "ServerController", description = "REST API контроллер для работы с серверами")
@RestController
@RequestMapping("/servers")
class ServerController(private val serverService: ServerService) : ServerClient {
    @Operation(summary = "Сохранить сервер")
    @PostMapping
    override suspend fun saveServer(@RequestBody serverDto: ServerDto) =
        serverService.saveServer(serverDto.toEntity()).toDto()

    @GetMapping
    override fun getUserServers(@RequestParam userId: Long) =
        serverService.findUserServers(userId).map { it.toDto() }
}