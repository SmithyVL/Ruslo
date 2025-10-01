package tech.ruslo.server.database.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.common.database.BaseEntity

/**
 * Сущность, которая хранит в себе информацию об участнике сервера.
 *
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property joiningDate дата присоединения к серверу.
 * @property roles роли участника.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Member(val serverId: Long, val userId: Long) : BaseEntity() {
    @CreatedDate
    lateinit var joiningDate: Instant

    @Transient
    lateinit var roles: List<Role>
}