package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.ChannelDto
import ru.blimfy.common.enums.ChannelTypes
import ru.blimfy.common.enums.ChannelTypes.TEXT
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Модель с информацией о канале.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property type тип.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Channel(val serverId: UUID, val name: String, val type: ChannelTypes = TEXT) : WithBaseData {
    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}

/**
 * Возвращает DTO представление сущности категории.
 */
fun Channel.toDto() = ChannelDto(id, serverId, name, type, createdDate, updatedDate)

/**
 * Возвращает сущность категории из DTO.
 */
fun ChannelDto.toEntity() = Channel(serverId, name, type).apply {
    this@toEntity.id?.let { id = it }
    this@toEntity.createdDate?.let { createdDate = it }
    this@toEntity.updatedDate?.let { updatedDate = it }
}