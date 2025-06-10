package ru.blimfy.gateway.api.channel.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

/**
 * DTO с новой информацией о канале.
 *
 * @property name название.
 * @property nsfw является ли канал NSFW.
 * @property topic тема.
 * @property icon иконка.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Новая информация о канале")
data class ModifyChannelDto(
    val nsfw: Boolean? = null,
    @Size(max = 1024, message = "Channel topic must be between 0 and 1024 characters long")
    val topic: String? = null,
    @Size(min = 1, max = 100, message = "Channel names must be between 1 and 100 characters long")
    val name: String,
    val icon: String? = null,
)