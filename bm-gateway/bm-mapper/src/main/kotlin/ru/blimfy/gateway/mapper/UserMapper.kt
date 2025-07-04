package ru.blimfy.gateway.mapper

import java.util.UUID
import org.springframework.stereotype.Component
import ru.blimfy.domain.server.usecase.member.MemberService
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.dto.websockets.WsUserDto

/**
 * Маппер для превращения пользователя в DTO и обратно.
 *
 * @property memberService сервис для работы с участниками серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class UserMapper(private val memberService: MemberService) {
    /**
     * Возвращает DTO представление [userDto] со связанной информацией и об участии на сервере с [serverId].
     */
    suspend fun toWsDto(userDto: UserDto, serverId: UUID? = null) =
        userDto.toWsDto().apply {
            serverId?.let {
                member = memberService.findMember(serverId, userDto.id).toPartialDto()
            }
        }
}

/**
 * Возвращает DTO представление с информацией из сущности пользователя.
 */
fun User.toDto() =
    UserDto(id, username, email, verified, globalName, avatar, bannerColor, createdDate)

/**
 * Возвращает DTO представление с информацией из сущности пользователя с информацией как об участнике сервера.
 */
fun UserDto.toWsDto() =
    WsUserDto(id, username, email, verified, globalName, avatar, bannerColor, createdDate)