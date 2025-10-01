package tech.ruslo.channel.database.entity

import org.springframework.data.relational.core.mapping.Table
import tech.ruslo.common.database.BaseEntity

/**
 * Сущность с информацией о канале.
 *
 * @property type тип.
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property position номер сортировки каналов внутри сервера.
 * @property parentId идентификатор родительского канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
class Channel(val type: String, val serverId: Long) : BaseEntity() {
    lateinit var name: String
    var position: Long = 0
    var parentId: Long? = null
}