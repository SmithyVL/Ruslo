package ru.blimfy.direct.usecase.channel

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.enumeration.DmChannelTypes
import ru.blimfy.common.exception.AccessDeniedException
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.direct.db.entity.DmChannel
import ru.blimfy.direct.db.repository.DmChannelRepository
import ru.blimfy.direct.usecase.exception.DmErrors.DM_CHANNEL_BY_ID_NOT_FOUND
import ru.blimfy.direct.usecase.exception.DmErrors.DM_CHANNEL_VIEW_ACCESS_DENIED
import ru.blimfy.direct.usecase.exception.DmErrors.GROUP_DM_MODIFY_ACCESS_DENIED

/**
 * Реализация интерфейса для работы с личными диалогами или группами.
 *
 * @property dmChannelRepo репозиторий для работы с личными диалогами или группами в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class DmChannelServiceImpl(private val dmChannelRepo: DmChannelRepository) : DmChannelService {
    override suspend fun createDmChannel(recipients: Set<UUID>, type: DmChannelTypes, ownerId: UUID?) =
        dmChannelRepo.save(DmChannel(type, recipients).apply { this.ownerId = ownerId })

    override suspend fun modifyGroupDm(id: UUID, newName: String, newIcon: String?) =
        findDmChannel(id)
            .apply {
                name = newName
                icon = newIcon
            }
            .let { dmChannelRepo.save(it) }

    override suspend fun addRecipientsGroupDm(id: UUID, newRecipients: Set<UUID>) =
        findDmChannel(id).apply { recipients += newRecipients }.let { dmChannelRepo.save(it) }

    override suspend fun deleteRecipientGroupDm(id: UUID, recipientId: UUID) =
        findDmChannel(id).apply { recipients -= recipientId }.let { dmChannelRepo.save(it) }

    override suspend fun changeOwnerGroupDm(id: UUID, newOwnerId: UUID) =
        findDmChannel(id).apply { ownerId = newOwnerId }.let { dmChannelRepo.save(it) }

    override suspend fun findDmChannel(id: UUID) =
        dmChannelRepo.findById(id) ?: throw NotFoundException(DM_CHANNEL_BY_ID_NOT_FOUND.msg.format(id))

    override fun findDmChannels(recipientId: UUID) =
        dmChannelRepo.findAllByRecipientId(recipientId)

    override suspend fun findDm(recipients: Set<UUID>) =
        dmChannelRepo.findByRecipientOneAndRecipientTwoAndTypeIsDm(recipients.elementAt(0), recipients.elementAt(1))

    override suspend fun checkDmChannelViewAccess(id: UUID, userId: UUID) {
        if (findDmChannel(id).recipients.contains(userId)) {
            throw AccessDeniedException(DM_CHANNEL_VIEW_ACCESS_DENIED.msg.format(id))
        }
    }

    override suspend fun checkGroupDmWriteAccess(id: UUID, userId: UUID) {
        if (userId != findDmChannel(id).ownerId) {
            throw AccessDeniedException(GROUP_DM_MODIFY_ACCESS_DENIED.msg.format(id))
        }
    }
}