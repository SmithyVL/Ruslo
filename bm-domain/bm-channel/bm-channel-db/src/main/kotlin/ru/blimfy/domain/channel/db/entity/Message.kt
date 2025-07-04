package ru.blimfy.domain.channel.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.domain.channel.db.entity.base.BaseEntity
import ru.blimfy.domain.converter.MessageReference
import ru.blimfy.domain.converter.MessageSnapshot
import ru.blimfy.domain.converter.enumerations.MessageTypes
import ru.blimfy.domain.converter.enumerations.MessageTypes.DEFAULT

/**
 * Сущность с информацией о сообщениях каналов.
 *
 * @property channelId идентификатор канала.
 * @property authorId идентификатор пользователя, создавшего сообщение.
 * @property type тип сообщения.
 * @property content содержимое сообщения.
 * @property messageReference информация о пересланном сообщении.
 * @property messageSnapshot моментальный снимок связанного сообщения.
 * @property mentions идентификаторы упомянутых пользователей.
 * @property mentionEveryone упоминает ли это сообщение всех.
 * @property pinned является ли сообщение закреплённым.
 * @property position обычно возрастающее целое число, которое представляет собой приблизительное положение сообщения.
 * @property createdDate дата создания.
 * @property updatedDate дата обновления.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class Message(val channelId: UUID, val authorId: UUID, val type: MessageTypes = DEFAULT) : BaseEntity() {
    var content: String? = null
    var messageReference: MessageReference? = null
    var messageSnapshot: MessageSnapshot? = null
    var mentions: Set<UUID>? = null
    var mentionEveryone: Boolean = false
    var pinned: Boolean = false
    var position: Long = 0

    @CreatedDate
    lateinit var createdDate: Instant

    @LastModifiedDate
    var updatedDate: Instant? = null

    var referencedMessage: Message? = null
}

/**
 * Возвращает моментальный снимок сообщения.
 */
fun Message.toSnapshot() =
    MessageSnapshot(content!!, authorId, createdDate, type, mentions, updatedDate)