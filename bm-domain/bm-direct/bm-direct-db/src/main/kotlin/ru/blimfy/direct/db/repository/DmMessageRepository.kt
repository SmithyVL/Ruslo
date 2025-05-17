package ru.blimfy.direct.db.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.direct.db.entity.DmMessage

/**
 * Репозиторий для работы с сущностью сообщения личного канала в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface DmMessageRepository : CoroutineCrudRepository<DmMessage, UUID> {
    /**
     * Возвращает страницу сущностей личных сообщений для личного диалога или группы с идентификатором [dmChannelId] по
     * конфигурации [pageable] постраничного поиска.
     */
    fun findAllByDmChannelId(dmChannelId: UUID, pageable: Pageable): Flow<DmMessage>

    /**
     * Возвращает страницу закреплённых сущностей личных сообщений для личного диалога или группы с идентификатором
     * [dmChannelId] по конфигурации [pageable] постраничного поиска.
     */
    fun findAllByDmChannelIdAndPinnedIsTrue(dmChannelId: UUID, pageable: Pageable): Flow<DmMessage>

    /**
     * Удаляет сущность личного сообщения с [id] от пользователя с идентификатором [authorId].
     */
    suspend fun deleteByIdAndAuthorId(id: UUID, authorId: UUID)
}