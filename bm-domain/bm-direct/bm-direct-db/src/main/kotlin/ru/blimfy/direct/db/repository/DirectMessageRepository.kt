package ru.blimfy.direct.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.direct.db.entity.DirectMessage

/**
 * Репозиторий для работы с сущностью сообщения личного диалога в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface DirectMessageRepository : CoroutineCrudRepository<DirectMessage, UUID> {
    /**
     * Возвращает страницу сущностей личных сообщений для личного диалога с идентификатором [conservationId] по
     * конфигурации [pageable].
     */
    fun findAllByConservationId(conservationId: UUID, pageable: Pageable): Flow<DirectMessage>

    /**
     * Удаляет сущность личного сообщения с [id] от пользователя с [authorId].
     */
    suspend fun deleteByIdAndAuthorId(id: UUID, authorId: UUID)
}