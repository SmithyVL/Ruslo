package ru.blimfy.gateway.service.textmessage

import java.util.UUID
import ru.blimfy.gateway.dto.message.text.NewTextMessageDto
import ru.blimfy.gateway.dto.message.text.TextMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Интерфейс для работы с обработкой запросов о сообщениях каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface TextMessageControllerService {
    /**
     * Возвращает новое текстовое [message], создаваемое [user].
     */
    suspend fun createMessage(message: NewTextMessageDto, user: CustomUserDetails): TextMessageDto

    /**
     * Возвращает обновлённое текстовое [message], обновляемое [user].
     */
    suspend fun modifyMessage(message: TextMessageDto, user: CustomUserDetails): TextMessageDto

    /**
     * Удаляет текстовое сообщение с [messageId], удаляемое [user].
     */
    suspend fun deleteMessage(messageId: UUID, user: CustomUserDetails)
}