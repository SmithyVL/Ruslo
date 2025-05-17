package ru.blimfy.direct.usecase.message

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.direct.db.entity.DmMessage

/**
 * Интерфейс для работы с сообщениями личных каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface DmMessageService {
    /**
     * Возвращает новое [dmMessage].
     */
    suspend fun createMessage(dmMessage: DmMessage): DmMessage

    /**
     * Возвращает личное сообщение с [id] и [newContent].
     */
    suspend fun setContent(id: UUID, newContent: String): DmMessage

    /**
     * Возвращает личное сообщение с [id] и [newPinned].
     */
    suspend fun setPinned(id: UUID, newPinned: Boolean): DmMessage

    /**
     * Возвращает личное сообщение с [id].
     */
    suspend fun findMessage(id: UUID): DmMessage

    /**
     * Возвращает [pageNumber] страницу с [pageSize] сообщениями личного канала с [dmChannelId].
     */
    suspend fun findMessages(dmChannelId: UUID, pageNumber: Int, pageSize: Int): Flow<DmMessage>

    /**
     * Возвращает [pageNumber] страницу с [pageSize] закреплёнными сообщениями личного канала с [dmChannelId].
     */
    suspend fun findPinnedMessages(dmChannelId: UUID, pageNumber: Int, pageSize: Int): Flow<DmMessage>

    /**
     * Удаляет личное сообщение с [id] от [authorId].
     */
    suspend fun deleteMessage(id: UUID, authorId: UUID): DmMessage
}