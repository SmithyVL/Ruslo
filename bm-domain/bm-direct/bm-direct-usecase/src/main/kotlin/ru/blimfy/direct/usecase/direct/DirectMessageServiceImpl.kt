package ru.blimfy.direct.usecase.direct

import java.util.UUID
import org.springframework.data.domain.PageRequest.of
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.domain.Sort.by
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
    override suspend fun saveMessage(directMessage: DirectMessage) =
        directMessageRepo.save(directMessage)

    override suspend fun findMessage(id: UUID) = directMessageRepo.findById(id)
        ?: throw NotFoundException(DIRECT_MESSAGE_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findConservationMessages(
        conservationId: UUID,
        pageNumber: Int,
        pageSize: Int,
    ) =
        directMessageRepo.findAllByConservationId(
            conservationId,
            of(pageNumber, pageSize, by(DESC, DIRECT_MESSAGE_SORT_FIELD)),
        )

    override suspend fun deleteMessage(id: UUID, authorId: UUID) =
        directMessageRepo.deleteByIdAndAuthorId(directMessageId = id, authorId = authorId)

    companion object {
        /**
         * Поле сортировки при поиске страницы личных сообщений.
         */
        const val DIRECT_MESSAGE_SORT_FIELD = "created_date"
    }
}