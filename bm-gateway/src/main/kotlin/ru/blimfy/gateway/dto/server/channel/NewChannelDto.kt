package ru.blimfy.gateway.dto.server.channel

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.server.db.entity.Channel

/**
 * DTO с информацией о новом канале сервера.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property type тип.
 * @property position номер сортировки каналов внутри сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Информация о новом канале сервера")
data class NewChannelDto(
    val serverId: UUID,
    @Size(min = 1, max = 100, message = "Channel names must be between 1 and 100 characters long") val name: String,
    val type: ChannelTypes,
    @PositiveOrZero(message = "Channel position must be positive") val position: Int,
)

/**
 * Возвращает сущность канала из DTO представления нового канала сервера.
 */
fun NewChannelDto.toEntity() = Channel(serverId, name, type, position)