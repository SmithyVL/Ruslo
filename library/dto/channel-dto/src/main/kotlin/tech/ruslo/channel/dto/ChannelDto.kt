package tech.ruslo.channel.dto

import tech.ruslo.channel.dto.enumerations.ChannelTypes
import tech.ruslo.channel.dto.enumerations.ChannelTypes.TEXT

/**
 * DTO с информацией о канале.
 *
 * @property id идентификатор.
 * @property type тип.
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property parentId идентификатор родительского канала.
 * @property position номер сортировки каналов внутри сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ChannelDto(
    val id: Long? = null,
    val type: ChannelTypes = TEXT,
    val serverId: Long,
    val name: String,
    val parentId: Long? = null,
    val position: Long? = null,
)