package ru.blimfy.server.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.server.db.entity.base.BaseEntity

/**
 * Сущность с информацией о канале сервера.
 *
 * @property serverId идентификатор сервера.
 * @property name название канала.
 * @property type тип канала, например, текстовый.
 * @property position номер сортировки каналов внутри сервера.
 * @property nsfw является ли канал NSFW.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Channel(val serverId: UUID, val name: String, val type: ChannelTypes, val position: Int) : BaseEntity() {
    var nsfw: Boolean = false
}