package ru.blimfy.direct.usecase.direct

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
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
    suspend fun saveDirectMessage(directMessage: DirectMessage): DirectMessage

    /**
     * Возвращает личное сообщение с [id].
     */
    suspend fun findDirectMessage(id: UUID): DirectMessage

    /**
     * Возвращает страницу с сообщениями личного диалога с идентификатором [conservationId] по конфигурации [pageable].
     */
    suspend fun findConservationDirectMessages(conservationId: UUID, pageable: Pageable): Flow<DirectMessage>

    /**
     * Удаляет личное сообщение с [id] от [authorId].
     */
    suspend fun deleteDirectMessage(id: UUID, authorId: UUID)
}