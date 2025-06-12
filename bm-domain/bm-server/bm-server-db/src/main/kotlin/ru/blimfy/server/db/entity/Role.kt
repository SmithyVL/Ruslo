package ru.blimfy.server.db.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
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
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Role(val serverId: UUID) : BaseEntity() {
    var name = ROLE_DEFAULT_NAME
    var permissions = ROLE_DEFAULT_PERMISSIONS
    var position = ROLE_DEFAULT_POSITION
    var color: String? = null
    var hoist = false
    var mentionable = false

    @CreatedDate
    lateinit var createdDate: Instant
}

/**
 * Дефолтное название роли.
 */
const val ROLE_DEFAULT_NAME = "@все"

/**
 * Дефолтная позиция роли.
 */
const val ROLE_DEFAULT_POSITION = 0

/**
 * Дефолтные разрешения роли.
 */
const val ROLE_DEFAULT_PERMISSIONS = "0"