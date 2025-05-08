package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о роли.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property basic флаг того, что роль является дефолтной для сервера.
 * @property color цвет.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Role(val serverId: UUID, val name: String, val basic: Boolean) : BaseEntity() {
    var color: String? = null
}