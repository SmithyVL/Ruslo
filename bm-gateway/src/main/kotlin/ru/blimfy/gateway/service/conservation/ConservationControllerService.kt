package ru.blimfy.gateway.service.conservation

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.gateway.dto.conservation.ConservationDto
import ru.blimfy.gateway.dto.conservation.NewConservationDto
import ru.blimfy.gateway.dto.message.direct.DirectMessageDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Интерфейс для работы с обработкой запросов о личных диалогах.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface ConservationControllerService {
    /**
     * Возвращает новый [newConservationDto].
     */
    suspend fun createConservation(newConservationDto: NewConservationDto): ConservationDto

    /**
     * Возвращает [pageNumber] страницу с [pageSize] сообщениями личного диалога с [conservationId], которые хочет
     * получить [user].
     */
    suspend fun findConservationDirectMessages(
        conservationId: UUID,
        pageNumber: Int,
        pageSize: Int,
        user: CustomUserDetails,
    ): Flow<DirectMessageDto>
}