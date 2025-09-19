package tech.ruslo.domain.server.usecase.ban

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.ruslo.domain.server.db.entity.Ban
import tech.ruslo.domain.server.db.repository.BanRepository
import tech.ruslo.domain.server.usecase.member.MemberService

/**
 * Реализация интерфейса для работы с банами сервера.
 *
 * @property repo репозиторий для работы с банами серверов в БД.
 * @property memberService сервис для работы с участниками серверов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class BanServiceImpl(private val repo: BanRepository, private val memberService: MemberService) : BanService {
    @Transactional
    override suspend fun createBan(ban: Ban) =
        ban.apply {
            position = getCountBans(serverId)
            repo.save(this)
            memberService.deleteUserMember(userId = ban.userId, serverId = ban.serverId)
        }

    override suspend fun findBan(serverId: UUID, userId: UUID) =
        repo.findByServerIdAndUserId(serverId, userId)

    override suspend fun findBans(serverId: UUID, limit: Int, before: UUID?, after: UUID?): Flow<Ban> {
        var end: Long
        var start: Long

        when {
            before != null -> {
                end = findBanOrThrow(before).position
                start = end - limit
            }

            after != null -> {
                start = findBanOrThrow(after).position
                end = start + limit
            }

            else -> {
                end = getCountBans(serverId)
                start = end - limit
            }
        }

        return repo.findPageBans(serverId, start, end)
    }

    override fun findBans(serverId: UUID) =
        repo.findAllByServerId(serverId)

    override suspend fun removeBan(serverId: UUID, userId: UUID) =
        findBan(serverId, userId)!!.apply {
            repo.deleteByServerIdAndUserId(serverId, userId)
        }

    /**
     * Возвращает бан с [id].
     */
    private suspend fun findBanOrThrow(id: UUID) =
        repo.findById(id)!!

    /**
     * Возвращает количество банов сервера с [serverId].
     */
    private suspend fun getCountBans(serverId: UUID) =
        repo.countByServerId(serverId)
}