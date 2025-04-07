package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Сущность, которая хранит в себе информацию об участнике личного диалога.
 *
 * @property conservationId идентификатор личного диалога.
 * @property userId идентификатор пользователя.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class MemberConservation(val conservationId: UUID, val userId: UUID) : WithBaseData {
    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}