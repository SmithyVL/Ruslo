package ru.blimfy.common.dto.channel

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID
import ru.blimfy.common.enums.ChannelTypes

/**
 * DTO с информацией о канале.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property name название.
 * @property type тип.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Информация о существующем канале сервера")
data class ChannelDto(val id: UUID, val serverId: UUID, val name: String, val type: ChannelTypes)