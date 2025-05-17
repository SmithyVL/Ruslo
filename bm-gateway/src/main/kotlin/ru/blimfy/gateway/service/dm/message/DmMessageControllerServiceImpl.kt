package ru.blimfy.gateway.service.dm.message

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.direct.usecase.channel.DmChannelService
import ru.blimfy.direct.usecase.message.DmMessageService
import ru.blimfy.gateway.dto.dm.message.DmMessageDto
import ru.blimfy.gateway.dto.dm.message.ModifyDmMessageDto
import ru.blimfy.gateway.dto.dm.message.NewDmMessageDto
import ru.blimfy.gateway.dto.dm.message.toDto
import ru.blimfy.gateway.dto.dm.message.toEntity
import ru.blimfy.gateway.dto.user.toDto
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.user.db.entity.User
import ru.blimfy.user.usecase.user.UserService
import ru.blimfy.websocket.dto.WsMessageTypes.DELETE_DM_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_DM_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_DM_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.PINNED_DM_MESSAGE

/**
 * Реализация интерфейса для работы с обработкой запросов о личных сообщениях.
 *
 * @property dmMessageService сервис для работы с личными сообщениями.
 * @property dmChannelService сервис для работы с личными каналами.
 * @property userService сервис для работы с пользователями.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class DmMessageControllerServiceImpl(
    private val dmMessageService: DmMessageService,
    private val dmChannelService: DmChannelService,
    private val userService: UserService,
    private val userWebSocketStorage: UserWebSocketStorage,
) : DmMessageControllerService {
    override suspend fun createDmMessage(newDmMessage: NewDmMessageDto, currentUser: User): DmMessageDto {
        val userId = currentUser.id
        val dmChannelId = newDmMessage.dmChannelId

        // Создать сообщение в личном канале может только его участник.
        dmChannelService.checkDmChannelViewAccess(id = dmChannelId, userId = userId)

        return dmMessageService.createMessage(newDmMessage.toEntity(userId))
            .let { it.toDto().apply { author = userService.findUser(author.id).toDto() } }
            .apply { userWebSocketStorage.sendDmChannelMessage(dmChannelId, NEW_DM_MESSAGE, this, userId) }
    }

    override suspend fun setDmMessageContent(
        dmMessageId: UUID,
        modifyDmMessage: ModifyDmMessageDto,
        currentUser: User,
    ): DmMessageDto {
        val userId = currentUser.id
        val dmChannelId = modifyDmMessage.dmChannelId

        // Обновить сообщение в личном канале может только его участник.
        dmChannelService.checkDmChannelViewAccess(id = dmChannelId, userId = userId)

        return dmMessageService.setContent(dmMessageId, modifyDmMessage.content)
            .let { it.toDto().apply { author = userService.findUser(author.id).toDto() } }
            .apply { userWebSocketStorage.sendDmChannelMessage(dmChannelId, EDIT_DM_MESSAGE, this, userId) }
    }

    override suspend fun deleteDmMessage(dmMessageId: UUID, currentUser: User): DmMessageDto {
        val userId = currentUser.id

        return dmMessageService.deleteMessage(id = dmMessageId, authorId = userId)
            .let { it.toDto().apply { author = userService.findUser(author.id).toDto() } }
            .apply { userWebSocketStorage.sendDmChannelMessage(dmChannelId, DELETE_DM_MESSAGE, dmMessageId, userId) }
    }

    override suspend fun setDmMessagePinned(dmMessageId: UUID, newPinned: Boolean, currentUser: User): DmMessageDto {
        val userId = currentUser.id
        val dmChannelId = dmMessageService.findMessage(dmMessageId).dmChannelId

        // Закрепить сообщение в личном канале может только его участник.
        dmChannelService.checkDmChannelViewAccess(id = dmChannelId, userId = userId)

        return dmMessageService.setPinned(dmMessageId, newPinned)
            .let { it.toDto().apply { author = userService.findUser(author.id).toDto() } }
            .apply { userWebSocketStorage.sendDmChannelMessage(dmChannelId, PINNED_DM_MESSAGE, this, userId) }
    }
}