package ru.blimfy.persistence.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.DirectMessage

/**
 * Репозиторий для работы с сущностью сообщения личного диалога в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface DirectMessageRepository : CoroutineCrudRepository<DirectMessage, UUID> {
    /**
     * Возвращает все сущности личных сообщений для личного диалога с идентификатором [conservationId].
     */
    fun findAllByConservationId(conservationId: UUID): Flow<DirectMessage>
}