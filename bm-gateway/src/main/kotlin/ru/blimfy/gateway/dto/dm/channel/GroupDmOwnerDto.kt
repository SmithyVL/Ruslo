package ru.blimfy.gateway.dto.dm.channel

import java.util.UUID

/**
 * DTO с информацией о новом владельце в групповом личном диалоге.
 *
 * @property ownerId новый владелец группового личного диалога.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class GroupDmOwnerDto(val ownerId: UUID)