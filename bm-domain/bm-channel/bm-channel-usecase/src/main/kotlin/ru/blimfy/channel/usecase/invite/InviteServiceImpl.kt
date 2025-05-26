package ru.blimfy.channel.usecase.invite

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.channel.db.entity.Invite
import ru.blimfy.channel.db.repository.InviteRepository
import ru.blimfy.channel.usecase.exception.ChannelErrors.INVITE_BY_ID_NOT_FOUND
import ru.blimfy.common.enumeration.InviteTypes
import ru.blimfy.common.enumeration.InviteTypes.SERVER
import ru.blimfy.common.exception.NotFoundException

/**
 * Реализация интерфейса для работы с приглашениями каналов.
 *
 * @property inviteRepo репозиторий для работы с приглашениями каналов в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class InviteServiceImpl(private val inviteRepo: InviteRepository) : InviteService {
    override suspend fun saveInvite(invite: Invite) =
        inviteRepo.save(invite)

    override suspend fun findInvite(id: UUID) =
        inviteRepo.findById(id) ?: throw NotFoundException(INVITE_BY_ID_NOT_FOUND.msg.format(id))

    override fun findInvites(parentId: UUID, type: InviteTypes?) = when (type) {
        SERVER -> inviteRepo.findAllByChannelId(parentId)
        else -> inviteRepo.findAllByServerId(parentId)
    }

    override suspend fun deleteInvite(id: UUID) =
        findInvite(id).apply { inviteRepo.deleteById(id) }
}