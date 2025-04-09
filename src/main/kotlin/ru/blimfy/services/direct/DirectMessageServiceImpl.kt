package ru.blimfy.services.direct

import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.blimfy.persistence.entity.DirectMessage
import ru.blimfy.persistence.repository.DirectMessageRepository

/**
 * Реализация интерфейса для работы с личными сообщениями.
 *
 * @property directMessageRepo репозиторий для работы с личными сообщениями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class DirectMessageServiceImpl(private val directMessageRepo: DirectMessageRepository) : DirectMessageService {
    override suspend fun saveDirectMessage(directMessage: DirectMessage) =
        directMessageRepo.save(directMessage)

    override suspend fun findConservationDirectMessages(conservationId: UUID, pageable: Pageable) =
        directMessageRepo.findAllByConservationId(conservationId, pageable)

    override suspend fun deleteDirectMessage(id: UUID) = directMessageRepo.deleteById(id)
}