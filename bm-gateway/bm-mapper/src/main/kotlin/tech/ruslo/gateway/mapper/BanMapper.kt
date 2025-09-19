package tech.ruslo.gateway.mapper

import org.springframework.stereotype.Component
import tech.ruslo.domain.server.db.entity.Ban
import tech.ruslo.domain.user.usecase.user.UserService
import tech.ruslo.gateway.dto.ban.BanDto

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