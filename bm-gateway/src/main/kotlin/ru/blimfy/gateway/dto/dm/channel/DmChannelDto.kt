package ru.blimfy.gateway.dto.dm.channel

import java.util.UUID
import ru.blimfy.common.enumeration.DmChannelTypes
import ru.blimfy.direct.db.entity.DmChannel
import ru.blimfy.gateway.dto.user.UserDto

/**
 * DTO с информацией о личном диалоге.
 *
 * @property id идентификатор.
 * @property type тип личного диалога.
 * @property ownerId идентификатор пользователя, создавшего группу.
 * @property name название группы.
 * @property icon иконка группы.
 * @property recipients пользователи личного диалога.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class DmChannelDto(
    val id: UUID,
    val type: DmChannelTypes,
    val ownerId: UUID? = null,
    val name: String? = null,
    val icon: String? = null,
) {
    lateinit var recipients: List<UserDto>
}

/**
 * Возвращает DTO представления сущности личного диалога или группы.
 */
fun DmChannel.toDto() = DmChannelDto(id, type, ownerId, name, icon)