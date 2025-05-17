package ru.blimfy.direct.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.direct.db.entity.base.BaseEntity

/**
 * Сущность с информацией о сообщении личного диалога или группы.
 *
 * @property dmChannelId идентификатор личного диалога или группы.
 * @property authorId идентификатор пользователя, создавшего сообщение.
 * @property content содержимое сообщения.
 * @property pinned является ли сообщение закреплённым.
 * @property createdDate дата создания.
 * @property updatedDate дата обновления.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class DmMessage(val dmChannelId: UUID, val authorId: UUID, var content: String? = null) : BaseEntity() {
    var pinned: Boolean = false

    @CreatedDate
    lateinit var createdDate: Instant

    @LastModifiedDate
    var updatedDate: Instant? = null
}