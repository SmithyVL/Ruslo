package ru.blimfy.gateway.service.directmessage

import java.util.UUID
import ru.blimfy.gateway.dto.message.direct.DirectMessageDto
import ru.blimfy.gateway.dto.message.direct.NewDirectMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Интерфейс для работы с обработкой запросов о личных сообщениях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface DirectMessageControllerService {
    /**
     * Возвращает новый [directMessageDto], который создаёт [user].
     */
    suspend fun createDirectMessage(directMessageDto: DirectMessageDto, user: CustomUserDetails): DirectMessageDto

    /**
     * Возвращает обновлённый [newDirectMessageDto], который обновляет [user].
     */
    suspend fun modifyDirectMessage(newDirectMessageDto: NewDirectMessageDto, user: CustomUserDetails): DirectMessageDto

    /**
     * Удаляет канал с [directMessageId], который удаляет [user].
     */
    suspend fun deleteDirectMessage(directMessageId: UUID, user: CustomUserDetails)
}