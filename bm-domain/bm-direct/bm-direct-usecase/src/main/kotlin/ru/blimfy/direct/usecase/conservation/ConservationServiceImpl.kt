package ru.blimfy.direct.usecase.conservation

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.common.exception.AccessDeniedException
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.direct.db.entity.Conservation
import ru.blimfy.direct.db.entity.MemberConservation
import ru.blimfy.direct.db.repository.ConservationRepository
import ru.blimfy.direct.usecase.conservation.member.MemberConservationService
import ru.blimfy.direct.usecase.exception.DirectErrors.CONSERVATION_ACCESS_DENIED
import ru.blimfy.direct.usecase.exception.DirectErrors.CONSERVATION_BY_ID_NOT_FOUND

/**
 * Реализация интерфейса для работы с приглашениями серверов.
 *
 * @property conservationRepo репозиторий для работы с личными диалогами в БД.
 * @property memberConservationService сервис для работы с участиями пользователей в личных диалогах.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ConservationServiceImpl(
    private val conservationRepo: ConservationRepository,
    private val memberConservationService: MemberConservationService,
) : ConservationService {
    @Transactional
    override suspend fun createConservation(firstUserId: UUID, secondUserId: UUID) =
        conservationRepo.save(Conservation()).apply {
            memberConservationService.saveMember(MemberConservation(this.id, firstUserId))
            memberConservationService.saveMember(MemberConservation(this.id, secondUserId))
        }

    override suspend fun findConservation(id: UUID) = conservationRepo.findById(id)
        ?: throw NotFoundException(CONSERVATION_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun checkConservationAccess(conservationId: UUID, userId: UUID) {
        try {
            memberConservationService.findMemberConservation(userId = userId, conservationId = conservationId)
        } catch (_: NotFoundException) {
            throw AccessDeniedException(CONSERVATION_ACCESS_DENIED.msg.format(conservationId))
        }
    }
}