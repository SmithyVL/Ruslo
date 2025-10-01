package tech.ruslo.channel.mapper

import tech.ruslo.channel.database.entity.Channel
import tech.ruslo.channel.dto.ChannelDto
import tech.ruslo.channel.dto.enumerations.ChannelTypes.valueOf

/**
 * Возвращает сущность канала из DTO представления канала.
 */
fun ChannelDto.toEntity() = Channel(type.name, serverId).apply {
    name = this@toEntity.name
    parentId = this@toEntity.parentId
}

/**
 * Возвращает DTO представление канала с информацией из сущности канала.
 */
fun Channel.toDto() = ChannelDto(id, valueOf(type), serverId, name, parentId, position)