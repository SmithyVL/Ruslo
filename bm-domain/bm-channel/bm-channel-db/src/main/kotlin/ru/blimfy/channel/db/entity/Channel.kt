package ru.blimfy.channel.db.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.channel.db.entity.base.BaseEntity
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.common.enumeration.ChannelTypes.GROUP_DM

/**
 * Сущность с информацией о канале.
 *
 * @property type тип.
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property icon иконка группы.
 * @property ownerId идентификатор создателя группы.
 * @property recipients идентификаторы участников.
 * @property parentId идентификатор родительского канала.
 * @property position номер сортировки каналов внутри сервера.
 * @property topic тема.
 * @property nsfw является ли канал NSFW.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class Channel(val type: ChannelTypes = GROUP_DM, val serverId: UUID? = null) : BaseEntity() {
    var name: String? = null
    var icon: String? = null
    var ownerId: UUID? = null
    var recipients: Set<UUID>? = null
    var parentId: UUID? = null
    var position: Long? = null
    var topic: String? = null
    var nsfw: Boolean? = null
}