package ru.blimfy.persistence.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.MemberConservation

/**
 * Репозиторий для работы с сущностью участника личного диалога в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface MemberConservationRepository : CoroutineCrudRepository<MemberConservation, UUID> {
    /**
     * Возвращает информацию пользователя с [userId] об участии в личных диалогах.
     */
    fun findAllByUserId(userId: UUID): Flow<MemberConservation>

    /**
     * Возвращает информацию личного диалога с [conservationId] об участии в нём пользователей.
     */
    fun findAllByConservationId(conservationId: UUID): Flow<MemberConservation>

    /**
     * Возвращает информацию пользователя с [userId] об участии в личном диалоге с [conservationId].
     */
    suspend fun findAllByUserIdAndConservationId(userId: UUID, conservationId: UUID): MemberConservation?
}