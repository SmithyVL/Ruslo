package ru.blimfy.domain.channel.api.dto.channel

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
data class ModifyChannelDto(
    val name: String,
    val nsfw: Boolean? = null,
    val topic: String? = null,
    val icon: String? = null,
)