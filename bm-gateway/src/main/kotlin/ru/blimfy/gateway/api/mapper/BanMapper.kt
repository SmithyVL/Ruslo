package ru.blimfy.gateway.api.mapper

import org.springframework.stereotype.Component
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.api.server.dto.ban.BanDto
import ru.blimfy.server.db.entity.Ban
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения банов в DTO и обратно.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class BanMapper(private val userService: UserService) {
    suspend fun toDto(ban: Ban) =
        BanDto(ban.id, ban.serverId, ban.reason).apply {
            user = userService.findUser(ban.userId).toDto()
        }
}