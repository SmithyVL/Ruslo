package ru.blimfy.gateway.api.server.dto.ban

import java.util.UUID
import ru.blimfy.gateway.api.dto.UserDto
import ru.blimfy.server.db.entity.Ban

/**
 * DTO с информацией о бане сервера.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property reason причина.
 * @property user заблокированный пользователь.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class BanDto(val id: UUID, val serverId: UUID, val reason: String? = null) {
    lateinit var user: UserDto
}

/**
 * Возвращает DTO представление сущности бана сервера.
 */
fun Ban.toDto() = BanDto(id, serverId, reason)