package ru.blimfy.direct.usecase.conservation.member

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.direct.db.entity.MemberConservation
import ru.blimfy.direct.db.repository.MemberConservationRepository
import ru.blimfy.direct.usecase.exception.DirectErrors.MEMBER_CONSERVATION_BY_ID_NOT_FOUND

/**
 * Реализация интерфейса для работы с участниками личных диалогов.
 *
 * @property memberConservationRepo репозиторий для работы с участниками личных диалогов в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberConservationServiceImpl(
    private val memberConservationRepo: MemberConservationRepository,
) : MemberConservationService {
    override suspend fun saveMember(member: MemberConservation) =
        memberConservationRepo.save(member)

    override suspend fun findMemberConservation(userId: UUID, conservationId: UUID) =
        memberConservationRepo.findAllByUserIdAndConservationId(userId, conservationId)
            ?: throw NotFoundException(MEMBER_CONSERVATION_BY_ID_NOT_FOUND.msg.format(conservationId))

    override fun findConservationMembers(conservationId: UUID) =
        memberConservationRepo.findAllByConservationId(conservationId)

    override fun findMemberConservations(userId: UUID) =
        memberConservationRepo.findAllByUserId(userId)
}