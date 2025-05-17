package ru.blimfy.server.usecase.member

import java.util.UUID
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import ru.blimfy.common.exception.DuplicateException
import ru.blimfy.common.exception.NotFoundException
import ru.blimfy.server.db.entity.Member
import ru.blimfy.server.db.repository.MemberRepository
import ru.blimfy.server.usecase.exception.ServerErrors.MEMBER_BY_ID_NOT_FOUND
import ru.blimfy.server.usecase.exception.ServerErrors.MEMBER_BY_USER_ID_AND_SERVER_ID_NOT_FOUND
import ru.blimfy.server.usecase.exception.ServerErrors.SERVER_MEMBER_ALREADY_EXISTS

/**
 * Реализация интерфейса для работы с участниками сервера.
 *
 * @property memberRepo репозиторий для работы с участниками серверов в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberServiceImpl(private val memberRepo: MemberRepository) : MemberService {
    override suspend fun saveMember(member: Member) =
        try {
            memberRepo.save(member)
        } catch (ex: DuplicateKeyException) {
            throw DuplicateException(SERVER_MEMBER_ALREADY_EXISTS.msg.format(member.serverId, member.userId), ex)
        }

    override suspend fun setNick(serverId: UUID, userId: UUID, newNick: String?) =
        findServerMember(serverId = serverId, userId = userId)
            .apply { nick = newNick }
            .let { memberRepo.save(it) }

    override suspend fun findServerMember(serverId: UUID, userId: UUID) =
        memberRepo.findByServerIdAndUserId(serverId = serverId, userId = userId)
            ?: throw NotFoundException(MEMBER_BY_USER_ID_AND_SERVER_ID_NOT_FOUND.msg.format(userId, serverId))

    override suspend fun findMember(id: UUID) = memberRepo.findById(id)
        ?: throw NotFoundException(MEMBER_BY_ID_NOT_FOUND.msg.format(id))

    override suspend fun getCountServerMembers(serverId: UUID) = memberRepo.countByServerId(serverId)

    override fun findUserMembers(userId: UUID) = memberRepo.findAllByUserId(userId)

    override fun findServerMembers(serverId: UUID) = memberRepo.findAllByServerId(serverId)

    override suspend fun deleteServerMember(memberId: UUID, serverId: UUID) =
        memberRepo.deleteByIdAndServerId(memberId, serverId)

    override suspend fun deleteUserMember(userId: UUID, serverId: UUID) =
        memberRepo.deleteByUserIdAndServerId(userId = userId, serverId = serverId)

    override suspend fun deleteServerMembers(serverId: UUID) =
        memberRepo.deleteAllByServerId(serverId)
}