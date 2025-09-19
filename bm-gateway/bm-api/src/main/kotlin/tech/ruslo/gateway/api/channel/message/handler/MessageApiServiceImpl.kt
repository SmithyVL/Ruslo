package tech.ruslo.gateway.api.channel.message.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import tech.ruslo.domain.channel.api.dto.message.ModifyMessageDto
import tech.ruslo.domain.channel.api.dto.message.NewMessageDto
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.channel.usecase.message.MessageService
import tech.ruslo.domain.user.db.entity.User
import tech.ruslo.gateway.access.control.service.AccessService
import tech.ruslo.gateway.dto.websockets.delete.MessageDeleteDto
import tech.ruslo.gateway.mapper.MessageMapper
import tech.ruslo.websocket.dto.enums.SendEvents.MESSAGE_CREATE
import tech.ruslo.websocket.dto.enums.SendEvents.MESSAGE_DELETE
import tech.ruslo.websocket.dto.enums.SendEvents.MESSAGE_UPDATE
import tech.ruslo.websocket.storage.UserWebSocketStorage

/**
 * Реализация интерфейса для работы с обработкой запросов о сообщениях.
 *
 * @property accessService сервис для работы с доступами.
 * @property messageService сервис для работы с сообщениями.
 * @property channelService сервис для работы с каналами.
 * @property messageMapper маппер для сообщений.
 * @property userWsStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MessageApiServiceImpl(
    private val accessService: AccessService,
    private val messageService: MessageService,
    private val channelService: ChannelService,
    private val messageMapper: MessageMapper,
    private val userWsStorage: UserWebSocketStorage,
) : MessageApiService {
    override suspend fun findMessages(
        channelId: UUID,
        around: UUID?,
        before: UUID?,
        after: UUID?,
        limit: Int,
        user: User,
    ) =
        accessService.isChannelViewAccess(channelId, user.id)
            .let { messageService.findMessages(channelId, before, after, around, limit) }
            .map { messageMapper.toDto(it) }

    override suspend fun createMessage(channelId: UUID, newMessageDto: NewMessageDto, user: User) =
        user.id.let { userId ->
            accessService.isChannelViewAccess(channelId, userId)
                .let { messageService.createMessage(newMessageDto, channelId, userId) }
                .let { messageMapper.toDto(it) }
                .apply { userWsStorage.sendMessage(MESSAGE_CREATE.name, messageMapper.toWsDto(this)) }
        }

    override suspend fun editMessage(
        channelId: UUID,
        id: UUID,
        modifyMessageDto: ModifyMessageDto,
        user: User,
    ) =
        user.id.let { userId ->
            accessService.isChannelViewAccess(channelId, userId)
                .let { messageService.setContent(id, modifyMessageDto.content) }
                .let { messageMapper.toDto(it) }
                .apply { userWsStorage.sendMessage(MESSAGE_UPDATE.name, messageMapper.toWsDto(this)) }
        }

    override suspend fun deleteMessage(channelId: UUID, id: UUID, user: User) =
        user.id.let { userId ->
            messageService.deleteMessage(id, userId)

            val serverId = channelService.findChannel(channelId).serverId
            userWsStorage.sendMessage(MESSAGE_DELETE.name, MessageDeleteDto(id, channelId, serverId))
        }
}