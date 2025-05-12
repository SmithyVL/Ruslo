package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о роли.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property permissions битовая маска с разрешениями.
 * @property position номер в сортировке ролей внутри сервера.
 * @property color цвет роли.
 * @property hoist если роль закреплена в списке участников канала сервера.
 * @property mentionable является ли роль упоминаемой.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Role(val serverId: UUID, val name: String, val permissions: String, val position: Int = 0) : BaseEntity() {
    var color: String? = null
    var hoist: Boolean = false
    var mentionable: Boolean = false
}