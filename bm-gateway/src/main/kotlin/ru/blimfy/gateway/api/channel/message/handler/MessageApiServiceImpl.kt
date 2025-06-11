package ru.blimfy.gateway.api.channel.message.handler

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.channel.usecase.channel.ChannelService
import ru.blimfy.channel.usecase.message.MessageService
import ru.blimfy.gateway.api.channel.dto.message.ModifyMessageDto
import ru.blimfy.gateway.api.channel.dto.message.NewMessageDto
import ru.blimfy.gateway.api.mapper.MemberMapper
import ru.blimfy.gateway.api.mapper.MessageMapper
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.gateway.integration.websockets.base.EntityDeleteDto
import ru.blimfy.gateway.service.AccessService
import ru.blimfy.user.db.entity.User
import ru.blimfy.websocket.dto.WsMessageTypes.MESSAGE_CREATE
import ru.blimfy.websocket.dto.WsMessageTypes.MESSAGE_DELETE
import ru.blimfy.websocket.dto.WsMessageTypes.MESSAGE_UPDATE

/**
 * Реализация интерфейса для работы с обработкой запросов о сообщениях.
 *
 * @property accessService сервис для работы с доступами.
 * @property messageService сервис для работы с сообщениями.
 * @property channelService сервис для работы с каналами.
 * @property messageMapper маппер для сообщений.
 * @property memberMapper маппер для участников серверов.
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
    private val memberMapper: MemberMapper,
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
            .let {
                var end: Long
                var start: Long

                when {
                    before != null -> {
                        end = messageService.findMessage(before).position
                        start = end - limit
                    }

                    after != null -> {
                        start = messageService.findMessage(after).position
                        end = start + limit
                    }

                    around != null -> {
                        val position = messageService.findMessage(around).position
                        start = position - limit
                        end = position + limit
                    }

                    else -> {
                        end = messageService.getCountMessages(channelId)
                        start = end - limit
                    }
                }

                messageService.findMessages(channelId, start, end)
            }
            .map { messageMapper.toDto(it) }

    override suspend fun createMessage(channelId: UUID, message: NewMessageDto, user: User) =
        user.id.let { userId ->
            accessService.isChannelViewAccess(channelId, userId)
                .let { messageMapper.toEntity(message, channelId, userId) }
                .let { messageService.createMessage(it) }
                .let { messageMapper.toDto(it) }
                .apply {
                    val extraFields = memberMapper.toWsDto(channelId, userId)
                    userWsStorage.sendMessage(MESSAGE_CREATE, this, extraFields)
                }
        }

    override suspend fun editMessage(channelId: UUID, id: UUID, message: ModifyMessageDto, user: User) =
        user.id.let { userId ->
            accessService.isChannelViewAccess(channelId, userId)
                .let { messageService.setContent(id, message.content) }
                .let { messageMapper.toDto(it) }
                .apply {
                    val extraFields = memberMapper.toWsDto(channelId, userId)
                    userWsStorage.sendMessage(MESSAGE_UPDATE, this, extraFields)
                }
        }

    override suspend fun deleteMessage(channelId: UUID, id: UUID, user: User) =
        user.id.let { userId ->
            messageService.deleteMessage(id, userId)

            // Отправляем WS событие об удалении сообщения в канале.
            val serverId = channelService.findChannel(channelId).serverId
            val data = EntityDeleteDto(id, channelId, serverId)
            userWsStorage.sendMessage(MESSAGE_DELETE, data)
        }
}