package ru.blimfy.gateway.service.dm.message

import java.util.UUID
import ru.blimfy.gateway.dto.dm.message.DmMessageDto
import ru.blimfy.gateway.dto.dm.message.ModifyDmMessageDto
import ru.blimfy.gateway.dto.dm.message.NewDmMessageDto
import ru.blimfy.user.db.entity.User

/**
 * Интерфейс для работы с обработкой запросов о личных сообщениях.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface DmMessageControllerService {
    /**
     * Возвращает [newDmMessage], которое создаёт [currentUser].
     */
    suspend fun createDmMessage(newDmMessage: NewDmMessageDto, currentUser: User): DmMessageDto

    /**
     * Возвращает личное сообщение с [dmMessageId] и обновлённой информацией [modifyDmMessage].
     */
    suspend fun setDmMessageContent(
        dmMessageId: UUID,
        modifyDmMessage: ModifyDmMessageDto,
        currentUser: User,
    ): DmMessageDto

    /**
     * Удаляет личное сообщение с [dmMessageId] от [currentUser].
     */
    suspend fun deleteDmMessage(dmMessageId: UUID, currentUser: User): DmMessageDto

    /**
     * Возвращает личное сообщение с [dmMessageId] и [newPinned], обновляемое [currentUser].
     */
    suspend fun setDmMessagePinned(dmMessageId: UUID, newPinned: Boolean, currentUser: User): DmMessageDto
}