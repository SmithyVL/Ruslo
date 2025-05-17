package ru.blimfy.direct.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.enumeration.DmChannelTypes
import ru.blimfy.direct.db.entity.base.BaseEntity

/**
 * Сущность с информацией о личном канале.
 *
 * @property type тип.
 * @property recipients идентификаторы участников.
 * @property ownerId идентификатор создателя группы.
 * @property name название группы.
 * @property icon иконка группы.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class DmChannel(val type: DmChannelTypes, var recipients: Set<UUID>) : BaseEntity() {
    var ownerId: UUID? = null
    var name: String? = null
    var icon: String? = null
}