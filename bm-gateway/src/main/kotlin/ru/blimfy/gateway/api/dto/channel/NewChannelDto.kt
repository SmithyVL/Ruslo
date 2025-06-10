package ru.blimfy.gateway.api.dto.channel

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.common.enumeration.ChannelTypes.TEXT

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
@Schema(description = "Информация о новом канале")
data class NewChannelDto(
    val type: ChannelTypes = TEXT,
    val name: String? = null,
    val parentId: UUID? = null,
    val recipients: Set<UUID>? = null,
)