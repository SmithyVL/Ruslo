package ru.blimfy.gateway.dto.dm.channel

import java.util.UUID
import ru.blimfy.common.enumeration.DmChannelTypes

/**
 * DTO с информацией о новом личном диалоге или группе.
 *
 * @property recipients идентификаторы пользователей.
 * @property type тип канала.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewDmChannelDto(val recipients: Set<UUID>, val type: DmChannelTypes)