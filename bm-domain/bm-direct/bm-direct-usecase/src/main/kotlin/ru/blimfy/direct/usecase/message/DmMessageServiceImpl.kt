package ru.blimfy.direct.usecase.message

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.common.util.DatabaseUtils.getDefaultPageable
import ru.blimfy.direct.db.entity.DmMessage
import ru.blimfy.direct.db.repository.DmMessageRepository
import ru.blimfy.direct.usecase.exception.DmErrors.DM_MESSAGE_BY_ID_NOT_FOUND

/**
 * Реализация интерфейса для работы с сообщениями личных каналов.
 *
 * @property dmMessageRepo репозиторий для работы с личными сообщениями в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class DmMessageServiceImpl(private val dmMessageRepo: DmMessageRepository) : DmMessageService {
    override suspend fun createMessage(dmMessage: DmMessage) = dmMessageRepo.save(dmMessage)

    override suspend fun setContent(id: UUID, newContent: String) =
        findMessage(id).apply { content = newContent }.let { dmMessageRepo.save(it) }

    override suspend fun setPinned(id: UUID, newPinned: Boolean) =
        findMessage(id).apply { pinned = newPinned }.let { dmMessageRepo.save(it) }

    override suspend fun findMessage(id: UUID) =
        dmMessageRepo.findById(id) ?: throw NotFoundException(DM_MESSAGE_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun findMessages(dmChannelId: UUID, pageNumber: Int, pageSize: Int) =
        dmMessageRepo.findAllByDmChannelId(dmChannelId, getDefaultPageable(pageNumber, pageSize))

    override suspend fun findPinnedMessages(dmChannelId: UUID, pageNumber: Int, pageSize: Int) =
        dmMessageRepo.findAllByDmChannelIdAndPinnedIsTrue(dmChannelId, getDefaultPageable(pageNumber, pageSize))

    override suspend fun deleteMessage(id: UUID, authorId: UUID) =
        findMessage(id).apply { dmMessageRepo.deleteByIdAndAuthorId(id = id, authorId = authorId) }
}