package ru.blimfy.server.usecase.invite

import java.util.UUID
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.server.db.entity.Invite
import ru.blimfy.server.db.repository.InviteRepository
import ru.blimfy.server.usecase.exception.ServerErrors.INVITE_BY_ID_NOT_FOUND

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

    override suspend fun deleteInvite(inviteId: UUID, serverId: UUID) =
        inviteRepo.deleteByIdAndServerId(inviteId = inviteId, serverId = serverId)
}