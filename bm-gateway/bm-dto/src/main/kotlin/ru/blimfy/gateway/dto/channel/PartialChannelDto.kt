package ru.blimfy.gateway.dto.channel

import java.util.UUID

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
data class PartialChannelDto(val id: UUID, val serverId: UUID? = null, val type: String, val name: String)