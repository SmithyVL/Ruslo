package ru.blimfy.services.member

import java.util.UUID
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import ru.blimfy.common.dto.MemberDto
import ru.blimfy.exception.Errors.MEMBER_BY_USER_ID_AND_SERVER_ID_NOT_FOUND
import ru.blimfy.exception.NotFoundException
import ru.blimfy.persistence.entity.Member
import ru.blimfy.persistence.entity.toDto
import ru.blimfy.persistence.entity.toEntity
import ru.blimfy.persistence.repository.MemberRepository

/**
 * Реализация интерфейса для работы с участниками сервера.
 *
 * @property memberRepo репозиторий для работы с участниками серверов в БД.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class MemberServiceImpl(private val memberRepo: MemberRepository) : MemberService {
    override suspend fun saveMember(member: MemberDto) = memberRepo.save(member.toEntity()).toDto()

    override suspend fun findServerMember(userId: UUID, serverId: UUID) =
        memberRepo.findByUserIdAndServerId(userId, serverId)
            ?.toDto()
            ?: throw NotFoundException(
                MEMBER_BY_USER_ID_AND_SERVER_ID_NOT_FOUND.msg.format(userId, serverId)
            )

    override fun findUserMembers(userId: UUID) =
        memberRepo.findAllByUserId(userId).map(Member::toDto)

    override fun findServerMembers(serverId: UUID) =
        memberRepo.findAllByServerId(serverId).map(Member::toDto)

    override suspend fun deleteServerMember(memberId: UUID, serverId: UUID) =
        memberRepo.deleteByIdAndServerId(memberId, serverId)

    override suspend fun deleteServerMembers(serverId: UUID) =
        memberRepo.deleteAllByServerId(serverId)
}