package ru.blimfy.direct.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.direct.db.entity.DmChannel

/**
 * Репозиторий для работы с сущностью личного канала в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface DmChannelRepository : CoroutineCrudRepository<DmChannel, UUID> {
    /**
     * Возвращает сущности личных каналов для [userId].
     */
    @Query("$SQL_SELECT where :recipientId = any(recipients)")
    fun findAllByRecipientId(userId: UUID): Flow<DmChannel>

    /**
     * Возвращает сущность личного диалога для [userIdOne] и [userIdTwo].
     */
    @Query("$SQL_SELECT where :userIdOne = any(recipients) and :userIdTwo = any(recipients) and type = 'DM'")
    suspend fun findByRecipientOneAndRecipientTwoAndTypeIsDm(userIdOne: UUID, userIdTwo: UUID): DmChannel

    private companion object {
        /**
         * Начальная часть SQL запроса про выборку данных.
         */
        private const val SQL_SELECT = "select * from dm_channel"
    }
}