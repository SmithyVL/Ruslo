package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Сущность, которая хранит в себе информацию о роли.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Role(val serverId: UUID, val name: String) : WithBaseData {
    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}