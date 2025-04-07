package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.enums.PrivilegeTypes
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Сущность, которая хранит в себе информацию о привилегии.
 *
 * @property roleId идентификатор роли.
 * @property type тип привилегии.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Privilege(val roleId: UUID, val type: PrivilegeTypes, val isGranted: Boolean = false) : WithBaseData {
    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}