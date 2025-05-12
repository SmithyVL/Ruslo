package ru.blimfy.gateway.service.directmessage

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.direct.usecase.conservation.ConservationService
import ru.blimfy.direct.usecase.direct.DirectMessageService
import ru.blimfy.gateway.dto.message.direct.DirectMessageDto
import ru.blimfy.gateway.dto.message.direct.NewDirectMessageDto
import ru.blimfy.gateway.dto.message.direct.toDto
import ru.blimfy.gateway.dto.message.direct.toEntity
import ru.blimfy.gateway.integration.security.CustomUserDetails
import ru.blimfy.gateway.integration.websockets.UserWebSocketStorage
import ru.blimfy.websocket.dto.WsMessageTypes.EDIT_DIRECT_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.NEW_DIRECT_MESSAGE
import ru.blimfy.websocket.dto.WsMessageTypes.REMOVE_DIRECT_MESSAGE

/**
 * Реализация интерфейса для работы с обработкой запросов о личных сообщениях.
 *
 * @property directMessageService сервис для работы с личными сообщениями.
 * @property conservationService сервис для работы с личными диалогами.
 * @property userWebSocketStorage хранилище для WebSocket соединений с ключом по идентификатору пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class DirectMessageControllerServiceImpl(
    private val directMessageService: DirectMessageService,
    private val conservationService: ConservationService,
    private val userWebSocketStorage: UserWebSocketStorage,
) : DirectMessageControllerService {
    override suspend fun createDirectMessage(
        directMessageDto: DirectMessageDto,
        user: CustomUserDetails,
    ): DirectMessageDto {
        val userId = user.userInfo.id
        val conservationId = directMessageDto.conservationId

        // Создать сообщение в личном диалоге может только его участник.
        conservationService.checkConservationAccess(conservationId = conservationId, userId = userId)

        return directMessageService.saveMessage(directMessageDto.toEntity(userId)).toDto()
            .apply { userWebSocketStorage.sendConservationMessages(conservationId, NEW_DIRECT_MESSAGE, this, userId) }
    }

    override suspend fun modifyDirectMessage(
        newDirectMessageDto: NewDirectMessageDto,
        user: CustomUserDetails
    ): DirectMessageDto {
        val userId = user.userInfo.id
        val conservationId = newDirectMessageDto.conservationId

        // Обновить сообщение в личном диалоге может только его участник.
        conservationService.checkConservationAccess(conservationId = conservationId, userId = userId)

        return directMessageService.saveMessage(newDirectMessageDto.toEntity(userId)).toDto()
            .apply { userWebSocketStorage.sendConservationMessages(conservationId, EDIT_DIRECT_MESSAGE, this, userId) }
    }

    override suspend fun deleteDirectMessage(directMessageId: UUID, user: CustomUserDetails) {
        val userId = user.userInfo.id

        directMessageService.deleteMessage(id = directMessageId, authorId = userId)
            .apply {
                userWebSocketStorage.sendConservationMessages(
                    directMessageService.findMessage(directMessageId).conservationId,
                    REMOVE_DIRECT_MESSAGE,
                    directMessageId,
                    userId,
                )
            }
    }
}