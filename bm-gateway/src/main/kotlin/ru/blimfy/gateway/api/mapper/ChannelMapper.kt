package ru.blimfy.gateway.api.mapper

import java.util.UUID
import org.springframework.stereotype.Component
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.gateway.api.channel.dto.ModifyChannelDto
import ru.blimfy.gateway.api.dto.channel.ChannelDto
import ru.blimfy.gateway.api.dto.channel.ChannelPartialDto
import ru.blimfy.gateway.api.dto.channel.NewChannelDto
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService

/**
 * Маппер для превращения канала в DTO и обратно.
 *
 * @property channelService сервис для работы с каналами.
 * @property userService сервис для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class ChannelMapper(private val channelService: ChannelService, private val userService: UserService) {
    /**
     * Возвращает сущность канала из [dto] для сервера с [serverId] (если он не указан, то создаётся личный канал) и
     * пользователя с [ownerId].
     */
    fun toEntity(dto: NewChannelDto, ownerId: UUID? = null, serverId: UUID? = null) =
        Channel(dto.type, serverId).apply {
            this.ownerId = ownerId
            recipients = dto.recipients
            name = dto.name
            parentId = dto.parentId
        }

    /**
     * Возвращает сущность канала с [channelId] из [dto].
     */
    suspend fun toEntity(dto: ModifyChannelDto, channelId: UUID) =
        channelService.findChannel(channelId).let {
            Channel(it.type, it.serverId).apply {
                nsfw = dto.nsfw
                topic = dto.topic
                name = dto.name
                icon = dto.icon
                id = it.id
                parentId = it.parentId
                position = it.position
                ownerId = it.ownerId
                recipients = it.recipients
            }
        }

    suspend fun toDto(channel: Channel) =
        channel.toBasicDto().apply {
            recipients = channel.recipients
                ?.map { recipientId -> userService.findUser(recipientId) }
                ?.map(User::toDto)
        }

    /**
     * Возвращает DTO частичного представления сущности [channel].
     */
    fun toPartialDto(channel: Channel) =
        ChannelPartialDto(channel.id, channel.serverId, channel.type, channel.name!!)

    /**
     * Возвращает DTO представление канала с базовой информацией.
     */
    private fun Channel.toBasicDto() =
        ChannelDto(id, type, serverId, name, icon, ownerId, parentId, position, topic, nsfw)
}