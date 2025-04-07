package ru.blimfy.services.conservation

import java.util.UUID
import ru.blimfy.persistence.entity.Conservation

/**
 * Интерфейс для работы с приглашениями на сервера.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ConservationService {
    /**
     * Возвращает новый личный диалог между двумя пользователями с [firstUserId] и [secondUserId].
     */
    suspend fun createConservation(firstUserId: UUID, secondUserId: UUID): Conservation
}