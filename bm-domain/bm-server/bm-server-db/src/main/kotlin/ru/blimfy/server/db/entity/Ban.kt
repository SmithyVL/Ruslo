package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о бане пользователя на сервере.
 *
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property reason причина.
 * @property position номер позиции внутри сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Ban(val serverId: UUID, val userId: UUID, val reason: String? = null) : BaseEntity() {
    var position: Long = 0
}