package ru.blimfy.gateway.service.conservation

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.direct.usecase.conservation.ConservationService
import ru.blimfy.direct.usecase.direct.DirectMessageService
import ru.blimfy.gateway.dto.conservation.NewConservationDto
import ru.blimfy.gateway.dto.conservation.toDto
import ru.blimfy.gateway.dto.message.direct.DirectMessageDto
import ru.blimfy.gateway.dto.message.direct.toDto
import ru.blimfy.gateway.integration.security.CustomUserDetails

/**
 * Реализация интерфейса для работы с обработкой запросов о личных диалогах.
 *
 * @property conservationService сервис для работы с личными диалогами.
 * @property directMessageService сервис для работы с личными сообщениями.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ConservationControllerServiceImpl(
    private val conservationService: ConservationService,
    private val directMessageService: DirectMessageService,
) : ConservationControllerService {
    override suspend fun createConservation(newConservationDto: NewConservationDto) =
        conservationService.createConservation(
            firstUserId = newConservationDto.firstUserId,
            secondUserId = newConservationDto.secondUserId,
        ).toDto()

    override suspend fun findConservationDirectMessages(
        conservationId: UUID,
        pageNumber: Int,
        pageSize: Int,
        user: CustomUserDetails,
    ): Flow<DirectMessageDto> {
        // Получить сообщения личного диалога может только его участник.
        conservationService.checkConservationAccess(conservationId = conservationId, userId = user.userInfo.id)

        return directMessageService.findConservationMessages(conservationId, pageNumber, pageSize).map { it.toDto() }
    }
}