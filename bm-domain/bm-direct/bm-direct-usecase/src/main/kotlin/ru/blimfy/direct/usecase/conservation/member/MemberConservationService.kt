package ru.blimfy.direct.usecase.conservation.member

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import ru.blimfy.direct.db.entity.MemberConservation

/**
 * Интерфейс для работы с участниками личных диалогов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
interface MemberConservationService {
    /**
     * Возвращает нового или обновлённого [member].
     */
    suspend fun saveMember(member: MemberConservation): MemberConservation

    /**
     * Возвращает информацию пользователя с [userId] об участии в личном диалоге с [conservationId].
     */
    suspend fun findMemberConservation(userId: UUID, conservationId: UUID): MemberConservation

    /**
     * Возвращает участия пользователей в личном диалоге с идентификатором [conservationId].
     */
    fun findConservationMembers(conservationId: UUID): Flow<MemberConservation>

    /**
     * Возвращает участия пользователя с идентификатором [userId] в личных диалогах.
     */
    fun findMemberConservations(userId: UUID): Flow<MemberConservation>
}