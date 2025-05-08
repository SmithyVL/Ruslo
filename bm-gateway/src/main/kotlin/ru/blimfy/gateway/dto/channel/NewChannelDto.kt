package ru.blimfy.gateway.dto.channel

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.server.db.entity.Channel

/**
 * DTO с информацией о новом канале сервера.
 *
 * @property name название.
 * @property type тип.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Информация о новом канале сервера")
data class NewChannelDto(val name: String, val type: ChannelTypes)

/**
 * Возвращает сущность канала из DTO представления нового канала сервера.
 */
fun NewChannelDto.toEntity(serverId: UUID) = Channel(serverId, name, type)