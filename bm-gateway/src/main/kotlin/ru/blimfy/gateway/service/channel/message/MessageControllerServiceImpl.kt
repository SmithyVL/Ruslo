package ru.blimfy.gateway.service.channel.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.message.MessageService
import ru.blimfy.common.enumeration.ChannelGroups.SERVER
import ru.blimfy.common.enumeration.ChannelGroups.USER
import ru.blimfy.gateway.dto.channel.message.MessageDto
import ru.blimfy.gateway.dto.channel.message.ModifyMessageDto
import ru.blimfy.gateway.dto.channel.message.NewMessageDto
import ru.blimfy.gateway.dto.channel.message.toDto
import ru.blimfy.gateway.dto.channel.message.toEntity
import ru.blimfy.gateway.dto.user.toDto
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
class MessageControllerServiceImpl(
    private val messageService: MessageService,
    private val channelService: ChannelService,
    private val serverService: ServerService,
    private val memberService: MemberService,
    private val userService: UserService,
    private val userWsStorage: UserWebSocketStorage,
) : MessageControllerService {
    override suspend fun findMessages(channelId: UUID, pageNumber: Int, pageSize: Int, user: User): Flow<MessageDto> {
        val userId = user.id

        checkMessageViewAccess(channelId, userId)

        return messageService.findMessages(channelId, pageNumber, pageSize)
            .map { it.toDto().apply { author = userService.findUser(it.authorId).toDto() } }
    }

    override suspend fun createMessage(channelId: UUID, message: NewMessageDto, user: User): MessageDto {
        val userId = user.id
        val extraFields: Any? = checkMessageViewAccess(channelId, userId)

        return messageService.createMessage(message.toEntity(channelId, userId))
            .let { it.toDto().apply { author = userService.findUser(author.id).toDto() } }
            .apply { userWsStorage.sendMessage(MESSAGE_CREATE, this, extraFields) }
    }

    override suspend fun editMessage(channelId: UUID, id: UUID, message: ModifyMessageDto, user: User): MessageDto {
        val userId = user.id
        val extraFields: Any? = checkMessageViewAccess(channelId, userId)

        return messageService.setContent(id, message.content)
            .let { it.toDto().apply { author = userService.findUser(author.id).toDto() } }
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
                    channelService.checkChannelViewAccess(id = channelId, userId = userId)
                    null
                }

                SERVER -> {
                    val serverId = channel.serverId!!
                    serverService.checkServerViewAccess(serverId = serverId, userId = userId)
                    val nick = memberService.findServerMember(serverId = serverId, userId = userId).nick
                    MemberInfoDto(serverId, PartialMemberDto(nick))
                }
            }
        }
}