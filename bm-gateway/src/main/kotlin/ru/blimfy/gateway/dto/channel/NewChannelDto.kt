package ru.blimfy.gateway.dto.channel

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.server.db.entity.Channel

/**
 * DTO с информацией о новом канале.
 *
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property type тип.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Информация о новом канале сервера")
data class NewChannelDto(val serverId: UUID, val name: String, val type: ChannelTypes)

/**
 * Возвращает сущность канала из DTO представления с новым каналом сервера.
 */
fun NewChannelDto.toEntity() = Channel(serverId, name, type)