package ru.blimfy.direct.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.direct.db.entity.base.BaseMessage

/**
 * Сущность с информацией о сообщениях личных диалогов.
 *
 * @param content содержимое сообщения.
 * @property authorId идентификатор автора сообщения.
 * @property conservationId идентификатор личного диалога.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class DirectMessage(content: String, val authorId: UUID, val conservationId: UUID) : BaseMessage(content)