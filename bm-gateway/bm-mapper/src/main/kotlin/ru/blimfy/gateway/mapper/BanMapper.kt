package ru.blimfy.gateway.mapper

import org.springframework.stereotype.Component
import ru.blimfy.domain.server.db.entity.Ban
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.dto.ban.BanDto

/**
 * Маппер для превращения сущности бана в DTO и обратно.
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