package ru.blimfy.persistence.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.channel.ChannelDto
import ru.blimfy.common.dto.channel.NewChannelDto
import ru.blimfy.common.enums.ChannelTypes
import ru.blimfy.persistence.entity.base.BaseEntity

/**
 * Сущность с информацией о канале сервера.
 *
 * @property serverId идентификатор сервера.
 * @property name название канала.
 * @property type тип канала, например, текстовый.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Channel(val serverId: UUID, val name: String, val type: ChannelTypes) : BaseEntity()

/**
 * Возвращает DTO представление сущности канала сервера.
 */
fun Channel.toDto() = ChannelDto(id, serverId, name, type)

/**
 * Возвращает сущность канала из DTO представления с новым каналом сервера.
 */
fun NewChannelDto.toEntity() = Channel(serverId, name, type)

/**
 * Возвращает сущность канала сервера из DTO представления.
 */
fun ChannelDto.toEntity() = Channel(serverId, name, type).apply {
    this.id = this@toEntity.id
}