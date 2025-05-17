package ru.blimfy.gateway.dto.server.channel

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.util.UUID

/**
 * DTO с новой информацией о канале сервера.
 *
 * @property id идентификатор.
 * @property name название.
 * @property nsfw является ли канал NSFW.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Новая информация о канале сервера")
data class ModifyChannelDto(
    val id: UUID,
    val nsfw: Boolean,
    @Size(min = 1, max = 100, message = "Channel names must be between 1 and 100 characters long") val name: String,
)