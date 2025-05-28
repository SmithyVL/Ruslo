package ru.blimfy.server.usecase.ban

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.blimfy.server.db.entity.Ban
import ru.blimfy.server.db.repository.BanRepository
import ru.blimfy.server.usecase.member.MemberService

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
    override suspend fun findBan(id: UUID) =
        repo.findById(id)!!

    override suspend fun findBan(serverId: UUID, userId: UUID) =
        repo.findByServerIdAndUserId(serverId = serverId, userId = userId)

    override fun findBans(serverId: UUID, start: Long, end: Long) =
        repo.findPageBans(serverId, start, end)

    override fun findBans(serverId: UUID) =
        repo.findAllByServerId(serverId)

    @Transactional
    override suspend fun createBan(ban: Ban) =
        ban.apply {
            position = getCountBans(serverId)
            repo.save(this)
            memberService.deleteUserMember(userId = ban.userId, serverId = ban.serverId)
        }

    override suspend fun removeBan(serverId: UUID, userId: UUID) =
        findBan(serverId = serverId, userId = userId)!!.apply { repo.delete(this) }

    override suspend fun getCountBans(serverId: UUID) =
        repo.countByServerId(serverId)
}