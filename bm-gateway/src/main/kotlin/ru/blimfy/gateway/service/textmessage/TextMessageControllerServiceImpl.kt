package ru.blimfy.gateway.service.textmessage

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.gateway.dto.server.message.NewTextMessageDto
import ru.blimfy.gateway.dto.server.message.TextMessageContentDto
import ru.blimfy.gateway.dto.server.message.TextMessageDto
import ru.blimfy.gateway.dto.server.message.toDto
import ru.blimfy.gateway.dto.server.message.toEntity
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.server.usecase.channel.ChannelService
import ru.blimfy.server.usecase.message.TextMessageService
import ru.blimfy.server.usecase.server.ServerService
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_TEXT_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_TEXT_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_TEXT_MESSAGE

/**
 * Реализация интерфейса для работы с обработкой запросов о сообщениях каналов.
 *
 * @property textMessageService сервис для работы с текстовыми сообщениями каналов серверов.
 * @property serverService сервис для работы с серверами.
 * @property channelService сервис для работы с каналами.
 * @property userService сервис для работы с пользователями.
 * @property userTokenWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class TextMessageControllerServiceImpl(
    private val textMessageService: TextMessageService,
    private val serverService: ServerService,
    private val channelService: ChannelService,
    private val userService: UserService,
    private val userTokenWebSocketStorage: UserWebSocketStorage,
) : TextMessageControllerService {
    override suspend fun createMessage(message: NewTextMessageDto, currentUser: User): TextMessageDto {
        val userId = currentUser.id
        val serverId = channelService.findChannel(message.channelId).serverId

        // Отправить текстовое сообщение в канал сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        return textMessageService.createMessage(message.toEntity(userId)).toDto()
            .apply {
                author = userService.findUser(userId).toDto()
                userTokenWebSocketStorage.sendServerMessages(serverId, NEW_TEXT_MESSAGE, this, userId)
            }
    }

    override suspend fun changeContent(messageId: UUID, messageContent: TextMessageContentDto, currentUser: User): TextMessageDto {
        val userId = currentUser.id
        val serverId = channelService.findChannel(messageContent.channelId).serverId

        // Изменить содержимое текстового сообщения канала сервера может только его участник.
        serverService.checkServerViewAccess(serverId = serverId, userId = userId)

        return textMessageService.setContent(messageId, messageContent.content).toDto()
            .apply {
                author = userService.findUser(userId).toDto()
                userTokenWebSocketStorage.sendServerMessages(serverId, EDIT_TEXT_MESSAGE, this, userId)
            }
    }

    override suspend fun deleteMessage(messageId: UUID, currentUser: User) {
        val userId = currentUser.id
        val channelId = textMessageService.findMessage(messageId).channelId
        val serverId = channelService.findChannel(channelId).serverId

        textMessageService.deleteMessage(textMessageId = messageId, authorId = userId)
            .apply { userTokenWebSocketStorage.sendServerMessages(serverId, REMOVE_TEXT_MESSAGE, messageId, userId) }
    }

    override suspend fun setMessagePinned(messageId: UUID, newPinned: Boolean, currentUser: User): TextMessageDto {
        val userId = currentUser.id
        val channelId = textMessageService.findMessage(messageId).channelId
        val serverId = channelService.findChannel(channelId).serverId

        // Закрепить/открепить текстовое сообщение канала сервера может только его владелец.
        serverService.checkServerModifyAccess(serverId = serverId, userId = userId)

        return textMessageService.setPinned(messageId, newPinned).toDto()
            .apply {
                author = userService.findUser(userId).toDto()
                userTokenWebSocketStorage.sendServerMessages(serverId, EDIT_TEXT_MESSAGE, this, userId)
            }
    }
}