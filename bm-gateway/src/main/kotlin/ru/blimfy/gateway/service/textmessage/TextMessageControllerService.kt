package ru.blimfy.gateway.service.textmessage

import java.util.UUID
import ru.blimfy.gateway.dto.server.message.NewTextMessageDto
import ru.blimfy.gateway.dto.server.message.TextMessageContentDto
import ru.blimfy.gateway.dto.server.message.TextMessageDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о сообщениях каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface TextMessageControllerService {
    /**
     * Возвращает новое текстовое [message], создаваемое [currentUser].
     */
    suspend fun createMessage(message: NewTextMessageDto, currentUser: User): TextMessageDto

    /**
     * Возвращает обновлённое сообщение с [messageId] и новым [messageContent], обновляемое [currentUser].
     */
    suspend fun changeContent(messageId: UUID, messageContent: TextMessageContentDto, currentUser: User): TextMessageDto

    /**
     * Удаляет текстовое сообщение с [messageId], удаляемое [currentUser].
     */
    suspend fun deleteMessage(messageId: UUID, currentUser: User)

    /**
     * Возвращает сообщение с [messageId] и [newPinned], обновляемое [currentUser].
     */
    suspend fun setMessagePinned(messageId: UUID, newPinned: Boolean, currentUser: User): TextMessageDto
}