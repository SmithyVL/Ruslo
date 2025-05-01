package ru.blimfy.direct.usecase.direct

import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.direct.db.entity.DirectMessage
import ru.blimfy.direct.db.repository.DirectMessageRepository
import ru.blimfy.direct.usecase.exception.DirectErrors.DIRECT_MESSAGE_BY_ID_NOT_FOUND

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

    override suspend fun findDirectMessage(id: UUID) = directMessageRepo.findById(id)
        ?: throw NotFoundException(DIRECT_MESSAGE_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findConservationDirectMessages(conservationId: UUID, pageable: Pageable) =
        directMessageRepo.findAllByConservationId(conservationId, pageable)

    override suspend fun deleteDirectMessage(id: UUID, authorId: UUID) =
        directMessageRepo.deleteByIdAndAuthorId(id, authorId)
}