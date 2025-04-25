package ru.blimfy.services.conservation

import java.util.UUID
import ru.blimfy.persistence.entity.Conservation

/**
 * Интерфейс для работы с личными диалогами пользователей.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ConservationService {
    /**
     * Возвращает новый личный диалог между двумя пользователями с [firstUserId] и [secondUserId].
     */
    suspend fun createConservation(firstUserId: UUID, secondUserId: UUID): Conservation

    /**
     * Возвращает личный диалог с [id].
     */
    suspend fun findConservation(id: UUID): Conservation

    /**
     * Проверяет разрешение пользователя с [userId] на личный диалог с [conservationId] и его связанных данных.
     */
    suspend fun checkConservationAccess(conservationId: UUID, userId: UUID)
}