package ru.blimfy.user.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.user.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о друге.
 *
 * @property fromId идентификатор пользователя, у которого есть друг.
 * @property toId идентификатор друга.
 * @property nick никнейм для друга.
 * @property createdDate дата появления друга.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Friend(val fromId: UUID, val toId: UUID) : BaseEntity() {
    var nick: String? = null

    @CreatedDate
    lateinit var createdDate: Instant
}