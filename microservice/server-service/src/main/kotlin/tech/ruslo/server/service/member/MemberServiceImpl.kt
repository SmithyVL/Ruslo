package tech.ruslo.server.service.member

import org.springframework.stereotype.Service
import tech.ruslo.server.database.entity.Member
import tech.ruslo.server.database.repository.MemberRepository

/**
 * Реализация интерфейса для работы с участниками сервера.
 *
 * @property memberRepo репозиторий для работы с участниками серверов в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberServiceImpl(private val memberRepo: MemberRepository) : MemberService {
    override suspend fun saveMember(member: Member) = memberRepo.save(member)

    override fun findUserMembers(userId: Long) = memberRepo.findAllByUserId(userId)

    override suspend fun removeMember(userId: Long, serverId: Long) =
        memberRepo.deleteByUserIdAndServerId(userId, serverId)
}