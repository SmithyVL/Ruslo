package ru.blimfy.direct.usecase.direct

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.direct.db.entity.DirectMessage

/**
 * Интерфейс для работы с личными сообщениями.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface DirectMessageService {
    /**
     * Возвращает новое или обновлённое [directMessage].
     */
    suspend fun saveMessage(directMessage: DirectMessage): DirectMessage

    /**
     * Возвращает личное сообщение с [id].
     */
    suspend fun findMessage(id: UUID): DirectMessage

    /**
     * Возвращает [pageNumber] страницу с [pageSize] сообщениями личного диалога с идентификатором [conservationId].
     */
    suspend fun findConservationMessages(conservationId: UUID, pageNumber: Int, pageSize: Int): Flow<DirectMessage>

    /**
     * Удаляет личное сообщение с [id] от [authorId].
     */
    suspend fun deleteMessage(id: UUID, authorId: UUID)
}