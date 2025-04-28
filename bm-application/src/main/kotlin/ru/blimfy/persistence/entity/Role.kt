package ru.blimfy.persistence.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.persistence.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о роли.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property isDefault флаг того, что роль является дефолтной для сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Role(val serverId: UUID, val name: String, val isDefault: Boolean) : BaseEntity()