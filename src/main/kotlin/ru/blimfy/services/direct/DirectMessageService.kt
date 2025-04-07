package ru.blimfy.services.direct

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.persistence.entity.DirectMessage

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
    suspend fun saveDirectMessage(directMessage: DirectMessage): DirectMessage

    /**
     * Возвращает сообщения личного диалога с идентификатором [conservationId].
     */
    suspend fun findConservationDirectMessages(conservationId: UUID): Flow<DirectMessage>

    /**
     * Удаляет личное сообщение с таким [id].
     */
    suspend fun deleteDirectMessage(id: UUID)
}