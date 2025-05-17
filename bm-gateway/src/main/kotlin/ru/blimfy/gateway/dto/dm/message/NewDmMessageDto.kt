package ru.blimfy.gateway.dto.dm.message

import java.util.UUID
import ru.blimfy.direct.db.entity.DmMessage

/**
 * DTO с информацией о новом личном сообщении.
 *
 * @property dmChannelId идентификатор личного диалога или группы.
 * @property content содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewDmMessageDto(val dmChannelId: UUID, val content: String? = null)

/**
 * Возвращает сущность сообщения личного канала для [authorId] из DTO представления.
 */
fun NewDmMessageDto.toEntity(authorId: UUID) = DmMessage(dmChannelId, authorId, content)