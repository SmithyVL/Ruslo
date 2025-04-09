package ru.blimfy.persistence.repository

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import ru.blimfy.persistence.entity.TextMessage

/**
 * Репозиторий для работы с сущностью текстового сообщения текстового канала в базе данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Repository
interface TextMessageRepository : CoroutineCrudRepository<TextMessage, UUID> {
    /**
     * Возвращает страницу сущностей текстовых сообщений для текстового канала с
     * идентификатором [channelId] по конфигурации [pageable].
     */
    fun findAllByChannelId(channelId: UUID, pageable: Pageable): Flow<TextMessage>
}