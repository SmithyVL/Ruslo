package tech.ruslo.websocket.dto.event.send

import tech.ruslo.gateway.dto.server.ServerDto
import tech.ruslo.gateway.dto.user.UserDto

/**
 * Информация об идентификации соединения.
 *
 * @property user токен авторизации.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ReadyDto(val user: UserDto, val servers: List<ServerDto>)