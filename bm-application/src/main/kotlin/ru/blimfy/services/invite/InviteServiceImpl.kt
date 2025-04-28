package ru.blimfy.services.invite

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.exception.Errors.INVITE_BY_ID_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.Invite
import ru.blimfy.persistence.repository.InviteRepository

/**
 * Реализация интерфейса для работы с приглашениями серверов.
 *
 * @property inviteRepo репозиторий для работы с приглашениями серверов в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class InviteServiceImpl(private val inviteRepo: InviteRepository) : InviteService {
    override suspend fun saveInvite(invite: Invite) = inviteRepo.save(invite)

    override suspend fun findInvite(inviteId: UUID) = inviteRepo.findById(inviteId)
        ?: throw NotFoundException(INVITE_BY_ID_NOT_FOUND.msg.format(inviteId))

    override fun findServerInvites(serverId: UUID) = inviteRepo.findAllByServerId(serverId)

    override suspend fun deleteInvite(id: UUID) = inviteRepo.deleteById(id)
}