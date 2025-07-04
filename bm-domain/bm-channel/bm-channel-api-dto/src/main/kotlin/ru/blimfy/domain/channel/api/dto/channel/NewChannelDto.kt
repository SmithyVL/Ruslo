package ru.blimfy.domain.channel.api.dto.channel

import java.util.UUID

/**
 * DTO с информацией о новом канале.
 *
 * @property type тип.
 * @property name название.
 * @property parentId идентификатор родительского канала.
 * @property recipients идентификаторы участников.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewChannelDto(
    val type: String,
    val name: String? = null,
    val parentId: UUID? = null,
    val recipients: Set<UUID>? = null,
)