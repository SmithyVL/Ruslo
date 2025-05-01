package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseMessage

/**
 * Сущность с информацией о сообщениях текстовых каналов.
 *
 * @param content содержимое сообщения.
 * @property authorId идентификатор автора сообщения.
 * @property channelId идентификатор текстового канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class TextMessage(content: String, val authorId: UUID, val channelId: UUID) : BaseMessage(content)