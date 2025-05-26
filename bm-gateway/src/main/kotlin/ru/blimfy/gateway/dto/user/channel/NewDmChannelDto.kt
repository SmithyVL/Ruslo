package ru.blimfy.gateway.dto.user.channel

import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes

/**
 * DTO с информацией о новом личном канале.
 *
 * @property recipients идентификаторы участников.
 * @property type тип канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewDmChannelDto(val recipients: Set<UUID>, val type: ChannelTypes)