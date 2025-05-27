package ru.blimfy.gateway.api.channel.message.handler

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.message.MessageService
import ru.blimfy.common.enumeration.ChannelGroups.SERVER
import ru.blimfy.common.enumeration.ChannelGroups.USER
import ru.blimfy.gateway.api.channel.dto.message.MessageDto
import ru.blimfy.gateway.api.channel.dto.message.ModifyMessageDto
import ru.blimfy.gateway.api.channel.dto.message.NewMessageDto
import ru.blimfy.gateway.api.channel.dto.message.toDto
import ru.blimfy.gateway.api.channel.dto.message.toEntity
import ru.blimfy.gateway.api.dto.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.integration.websockets.base.EntityDeleteDto
import ru.blimfy.gateway.integration.websockets.base.PartialMemberDto
import ru.blimfy.gateway.integration.websockets.extra.MemberInfoDto
import ru.blimfy.server.usecase.member.MemberService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.MESSAGE_CREATE
import ru.blimfy.websocket.dto.WsMessageTypes.MESSAGE_DELETE
import ru.blimfy.websocket.dto.WsMessageTypes.MESSAGE_UPDATE

/**
 * Реализация интерфейса для работы с обработкой запросов о сообщениях.
 *
 * @property messageService сервис для работы с сообщениями.
 * @property channelService сервис для работы с каналами.
 * @property userService сервис для работы с пользователями.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MessageApiServiceImpl(
    private val messageService: MessageService,
    private val channelService: ChannelService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : MessageApiService {
    override suspend fun findMessages(
        channelId: UUID,
        around: UUID?,
        before: UUID?,
        after: UUID?,
        limit: Int,
        user: User,
    ): Flow<MessageDto> {
        checkMessageViewAccess(channelId, user.id)

        return when {
            before != null -> {
                val end = messageService.findMessage(before).position
                val start = end - limit
                messageService.findMessages(channelId, start, end)
            }

            after != null -> {
                val start = messageService.findMessage(after).position
                val end = start + limit
                messageService.findMessages(channelId, start, end)
            }

            around != null -> {
                val position = messageService.findMessage(around).position
                val start = position - limit
                val end = position + limit
                messageService.findMessages(channelId, start, end)
            }

            else -> {
                val end = messageService.getCountMessages(channelId)
                val start = end - limit
                messageService.findMessages(channelId, start, end)
            }
        }.map { it.toDto().apply { author = userService.findUser(it.authorId).toDto() } }
    }

    override suspend fun createMessage(channelId: UUID, message: NewMessageDto, user: User): MessageDto {
        val userId = user.id
        val extraFields: Any? = checkMessageViewAccess(channelId, userId)

        return messageService.createMessage(message.toEntity(channelId, userId))
            .let { it.toDto().apply { author = userService.findUser(it.authorId).toDto() } }
            .apply { userWsStorage.sendMessage(MESSAGE_CREATE, this, extraFields) }
    }

    override suspend fun editMessage(channelId: UUID, id: UUID, message: ModifyMessageDto, user: User): MessageDto {
        val userId = user.id
        val extraFields: Any? = checkMessageViewAccess(channelId, userId)

        return messageService.setContent(id, message.content)
            .let { it.toDto().apply { author = userService.findUser(it.authorId).toDto() } }
            .apply { userWsStorage.sendMessage(MESSAGE_UPDATE, this, extraFields) }
    }

    override suspend fun deleteMessage(channelId: UUID, id: UUID, user: User) =
        user.id.let { userId ->
            messageService.deleteMessage(id = id, authorId = userId)

            // Отправляем WS событие об удалении сообщения в канале.
            val serverId = channelService.findChannel(channelId).serverId
            val data = EntityDeleteDto(id = id, channelId = channelId, serverId = serverId)
            userWsStorage.sendMessage(MESSAGE_DELETE, data)
        }

    /**
     * Возвращает дополнительную информацию о сообщении канала с [channelId] для пользователя с [userId].
     */
    private suspend fun checkMessageViewAccess(channelId: UUID, userId: UUID) =
        channelService.findChannel(channelId).let { channel ->
            // Создать/изменить сообщение в канале может только его участник или участник его сервера.
            when (channel.type.group) {
                USER -> {
                    channelService.checkChannelView(id = channelId, userId = userId)
                    null
                }

                SERVER -> {
                    val serverId = channel.serverId!!
                    serverService.checkServerView(serverId = serverId, userId = userId)
                    val nick = memberService.findServerMember(serverId = serverId, userId = userId).nick
                    MemberInfoDto(serverId, PartialMemberDto(nick))
                }
            }
        }
}