package tech.ruslo.server.database.entity

import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.common.database.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о сервере.
 *
 * @property ownerId идентификатор пользователя, владеющего сервером.
 * @property name название.
 * @property defaultRoleId идентификатор стандартной роли сервера. Появляется такая роль уже после создания сервера.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Server(val ownerId: Long, val name: String) : BaseEntity() {
    var defaultRoleId: Long? = null

    @CreatedDate
    lateinit var createdDate: Instant
}