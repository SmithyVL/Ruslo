package ru.blimfy.gateway.dto.channel

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes

/**
 * DTO с частичной информацией о канале.
 *
 * @property id идентификатор.
 * @property type тип.
 * @property name название.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Schema(description = "Частичная информация о канале")
@JsonInclude(NON_NULL)
data class ChannelPartialDto(val id: UUID, val type: ChannelTypes, val name: String)