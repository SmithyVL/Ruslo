package ru.blimfy.gateway.mapper

import java.util.UUID
import org.springframework.stereotype.Component
import ru.blimfy.domain.channel.db.entity.Message
import ru.blimfy.domain.channel.usecase.channel.ChannelService
import ru.blimfy.domain.converter.MessageSnapshot
import ru.blimfy.domain.user.db.entity.User
import ru.blimfy.domain.user.usecase.user.UserService
import ru.blimfy.gateway.dto.message.MessageDto
import ru.blimfy.gateway.dto.message.MessageSnapshotDto
import ru.blimfy.gateway.dto.user.UserDto
import ru.blimfy.gateway.dto.websockets.WsMessageDto

/**
 * Маппер для превращения сообщения в DTO и обратно.
 *
 * @property channelService сервис для работы с каналами.
 * @property userService сервис для работы с пользователями.
 * @property userMapper маппер для работы с пользователями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Component
class MessageMapper(
    private val channelService: ChannelService,
    private val userService: UserService,
    private val userMapper: UserMapper,
) {
    /**
     * Возвращает DTO представление [message] со связанной информацией.
     */
    suspend fun toDto(message: Message) =
        message.toBasicDto().apply {
            referencedMessage = message.referencedMessage?.toBasicDto()
            messageSnapshot = message.messageSnapshot?.toSnapshotDto()
        }

    /**
     * Возвращает DTO представление [messageDto] со связанной информацией.
     */
    suspend fun toWsDto(messageDto: MessageDto) =
        messageDto.toWsBasicDto().apply {
            referencedMessage = messageDto.referencedMessage
            messageSnapshot = messageDto.messageSnapshot

            val serverId = channelService.findChannel(channelId).serverId
            author = messageDto.author.let { userMapper.toWsDto(it, serverId) }
            mentions = mentionsToWsUsers(messageDto.mentions)
        }

    /**
     * Возвращает DTO представление сообщения с базовой информацией.
     */
    private suspend fun Message.toBasicDto() =
        MessageDto(
            id, channelId, type.name, pinned, content, mentionEveryone, messageReference,
            createdDate, updatedDate,
        ).apply {
            this@apply.author = userService.findUser(this@toBasicDto.authorId).toDto()
            this@apply.mentions = mentionsToUsers(this@toBasicDto.mentions)
        }

    /**
     * Возвращает DTO представление сообщения с базовой информацией.
     */
    private suspend fun MessageDto.toWsBasicDto() =
        WsMessageDto(
            id, channelId, type, pinned, content, mentionEveryone, messageReference,
            createdDate, updatedDate,
        )

    /**
     * Возвращает DTO представление снимка сообщения.
     */
    private suspend fun MessageSnapshot.toSnapshotDto() =
        MessageSnapshotDto(type, content, createdDate, updatedDate).apply {
            this@apply.author = userService.findUser(this@toSnapshotDto.authorId).toDto()
            this@apply.mentions = mentionsToUsers(this@toSnapshotDto.mentions)
        }

    /**
     * Возвращает список пользователей из [mentions].
     */
    private suspend fun mentionsToUsers(mentions: Set<UUID>?) =
        mentions
            ?.map { userService.findUser(it) }
            ?.map(User::toDto)

    /**
     * Возвращает список пользователей из [mentions] с информацией об участии на сервере.
     */
    private suspend fun mentionsToWsUsers(mentions: List<UserDto>?, serverId: UUID? = null) =
        mentions?.map { userMapper.toWsDto(it, serverId) }
}