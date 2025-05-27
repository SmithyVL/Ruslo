package ru.blimfy.gateway.api.user.dto.channel

import java.util.UUID
import ru.blimfy.common.enumeration.ChannelTypes
import ru.blimfy.common.enumeration.ChannelTypes.DM

/**
 * DTO с информацией о новом личном канале.
 *
 * @property recipients идентификаторы участников.
 * @property type тип канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewDmChannelDto(val recipients: Set<UUID> = emptySet(), val type: ChannelTypes = DM)