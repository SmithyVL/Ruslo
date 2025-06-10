package ru.blimfy.gateway.api.dto.channel

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.common.enumeration.ChannelTypes.TEXT

/**
 * DTO с частичной информацией о канале.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property type тип.
 * @property name название.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Частичная информация о канале")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ChannelPartialDto(
    val id: UUID,
    val serverId: UUID? = null,
    val type: ChannelTypes = TEXT,
    val name: String,
)