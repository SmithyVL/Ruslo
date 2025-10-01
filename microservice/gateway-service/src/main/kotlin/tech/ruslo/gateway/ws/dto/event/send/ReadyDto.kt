package tech.ruslo.gateway.ws.dto.event.send

import tech.ruslo.gateway.ws.dto.ShortServerDto
import tech.ruslo.gateway.ws.dto.ShortUserDto

/**
 * Информация об идентификации соединения.
 *
 * @property user информация о пользователе.
 * @property servers информация о серверах пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ReadyDto(val user: ShortUserDto, val servers: List<ShortServerDto>)