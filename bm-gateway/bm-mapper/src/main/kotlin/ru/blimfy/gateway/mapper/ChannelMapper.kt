package ru.blimfy.gateway.mapper

import org.springframework.stereotype.Component
import ru.blimfy.domain.channel.db.entity.Channel
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.dto.channel.ChannelDto
import ru.blimfy.gateway.dto.channel.PartialChannelDto

/**
 * Маппер для превращения канала в DTO и обратно.
 *
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class ChannelMapper(private val userService: UserService) {
    suspend fun toDto(channel: Channel) =
        ChannelDto(
            channel.id,
            channel.type.name,
            channel.serverId,
            channel.name,
            channel.icon,
            channel.ownerId,
            channel.parentId,
            channel.position,
            channel.topic,
            channel.nsfw,
        ).apply {
            recipients = channel.recipients
                ?.map { recipientId -> userService.findUser(recipientId) }
                ?.map(User::toDto)
        }
}

/**
 * Возвращает DTO частичного представления сущности канала.
 */
fun Channel.toPartialDto() =
    PartialChannelDto(id, serverId, type.name, name!!)

/**
 * Возвращает DTO представление канала с базовой информацией.
 */
fun Channel.toBasicDto() =
    ChannelDto(id, type.name, serverId, name, icon, ownerId, parentId, position, topic, nsfw)
