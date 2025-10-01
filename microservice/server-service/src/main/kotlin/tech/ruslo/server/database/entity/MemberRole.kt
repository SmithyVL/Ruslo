package tech.ruslo.server.database.entity

import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.common.database.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о роли участника сервера.
 *
 * @property memberId идентификатор участника сервера.
 * @property roleId идентификатор роли.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class MemberRole(val memberId: Long, val roleId: Long) : BaseEntity()