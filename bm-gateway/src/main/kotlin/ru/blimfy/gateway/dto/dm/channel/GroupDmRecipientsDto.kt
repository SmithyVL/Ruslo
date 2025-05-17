package ru.blimfy.gateway.dto.dm.channel

import java.util.UUID

/**
 * DTO с информацией о новых участниках в групповом личном диалоге.
 *
 * @property recipients идентификаторы участников.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class GroupDmRecipientsDto(val recipients: Set<UUID>)