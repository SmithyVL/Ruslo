package ru.blimfy.gateway.dto.user

import ru.blimfy.gateway.dto.server.ServerDto

/**
 * DTO с информацией текущего пользователя.
 *
 * @property user пользователь.
 * @property servers сервера пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class CurrentUserDto(val user: UserDto, val servers: List<ServerDto>)