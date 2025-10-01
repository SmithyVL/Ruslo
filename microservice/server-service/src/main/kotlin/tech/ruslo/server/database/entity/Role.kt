package tech.ruslo.server.database.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.common.database.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о роли.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property permissions битовая маска с разрешениями.
 * @property position номер в сортировке ролей внутри сервера.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Role(val serverId: Long) : BaseEntity() {
    var name = ROLE_DEFAULT_NAME
    var permissions = ROLE_DEFAULT_PERMISSIONS
    var position = ROLE_DEFAULT_POSITION

    @CreatedDate
    lateinit var createdDate: Instant
}

/**
 * Дефолтное название роли.
 */
const val ROLE_DEFAULT_NAME = "Новая роль"

/**
 * Дефолтная позиция роли.
 */
const val ROLE_DEFAULT_POSITION = 0

/**
 * Дефолтные разрешения роли.
 */
const val ROLE_DEFAULT_PERMISSIONS = "0"