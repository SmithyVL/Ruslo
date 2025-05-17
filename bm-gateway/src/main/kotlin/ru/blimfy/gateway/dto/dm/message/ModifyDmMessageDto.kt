package ru.blimfy.gateway.dto.dm.message

import java.util.UUID

/**
 * DTO с обновлённой информацией о личном сообщении.
 *
 * @property dmChannelId идентификатор личного канала.
 * @property content содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyDmMessageDto(val dmChannelId: UUID, val content: String)