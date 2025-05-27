package ru.blimfy.gateway.api.server.dto.channel

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.util.UUID
import ru.blimfy.channel.db.entity.Channel
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.common.enumeration.ChannelTypes.TEXT

/**
 * DTO с информацией о новом канале сервера.
 *
 * @property name название.
 * @property type тип.
 * @property parentId идентификатор родительского канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Информация о новом канале сервера")
data class ServerChannelDto(
    @Size(min = 1, max = 100, message = "Channel names must be between 1 and 100 characters long")
    val name: String,
    val type: ChannelTypes = TEXT,
    val parentId: UUID? = null,
)

/**
 * Возвращает сущность канала из DTO представления.
 */
fun ServerChannelDto.toEntity(serverId: UUID) = Channel(type, serverId).apply {
    parentId = this@toEntity.parentId
    name = this@toEntity.name
}