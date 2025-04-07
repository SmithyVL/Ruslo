package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Модель с информацией о сообщениях текстовых каналов.
 *
 * @property authorId идентификатор автора сообщения.
 * @property channelId идентификатор текстового канала.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class TextMessage(val authorId: UUID, val channelId: UUID, val content: String) : WithBaseData {
    private var fileUrl: UUID? = null

    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}