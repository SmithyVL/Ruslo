package tech.ruslo.domain.channel.usecase.invite

import java.util.UUID
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Service
import tech.ruslo.common.exception.NotFoundException
import tech.ruslo.domain.channel.db.entity.Invite
import tech.ruslo.domain.channel.db.repository.InviteRepository
import tech.ruslo.domain.channel.usecase.channel.ChannelService
import tech.ruslo.domain.channel.usecase.exception.ChannelErrors.INVITE_NOT_FOUND

/**
 * Реализация интерфейса для работы с приглашениями каналов.
 *
 * @property repo репозиторий для работы с приглашениями каналов в БД.
 * @property channelService сервис для работы с каналами.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class InviteServiceImpl(
    private val repo: InviteRepository,
    private val channelService: ChannelService,
) : InviteService {
    override suspend fun createInvite(invite: Invite) =
        repo.save(invite).apply { fetchChannel() }

    override suspend fun findInvite(id: UUID) =
        findInviteOrThrow(id).apply { fetchChannel() }

    override fun findServerInvites(serverId: UUID) =
        repo.findAllByServerId(serverId).onEach { it.fetchChannel() }

    override fun findChannelInvites(channelId: UUID) =
        repo.findAllByChannelId(channelId).onEach { it.fetchChannel() }

    override suspend fun deleteInvite(id: UUID) =
        repo.deleteById(id)

    /**
     * Возвращает приглашение с [id] или выбрасывает исключение, если оно не найдено.
     */
    private suspend fun findInviteOrThrow(id: UUID) =
        repo.findById(id) ?: throw NotFoundException(INVITE_NOT_FOUND.msg)

    /**
     * Подгружает канал в приглашение.
     */
    private suspend fun Invite.fetchChannel() =
        apply {
            channel = channelService.findChannel(channelId)
        }
}