package ru.blimfy.services.conservation

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.persistence.entity.Conservation
import ru.blimfy.persistence.entity.MemberConservation
import ru.blimfy.persistence.repository.ConservationRepository
import ru.blimfy.persistence.repository.MemberConservationRepository
import ru.blimfy.persistence.repository.UserRepository

/**
 * Реализация интерфейса для работы с приглашениями серверов.
 *
 * @property conservationRepo репозиторий для работы с личными диалогами в БД.
 * @property memberConservationRepo репозиторий для работы с участниками личных диалогов в БД.
 * @property userRepo репозиторий для работы с пользователями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ConservationServiceImpl(
    private val conservationRepo: ConservationRepository,
    private val memberConservationRepo: MemberConservationRepository,
    private val userRepo: UserRepository,
) : ConservationService {
    @Transactional
    override suspend fun createConservation(firstUserId: UUID, secondUserId: UUID) =
        conservationRepo.save(Conservation()).apply {
            memberConservationRepo.save(MemberConservation(this.id, firstUserId))
            memberConservationRepo.save(MemberConservation(this.id, secondUserId))
        }
}