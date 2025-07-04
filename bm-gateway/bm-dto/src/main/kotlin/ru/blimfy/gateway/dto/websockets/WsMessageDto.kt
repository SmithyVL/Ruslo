package ru.blimfy.gateway.dto.websockets

import java.time.Instant
import java.util.UUID
import ru.blimfy.domain.converter.MessageReference
import ru.blimfy.gateway.dto.message.MessageDto
import ru.blimfy.gateway.dto.message.MessageSnapshotDto

/**
 * DTO с информацией о сообщении, расширенной информацией о сервере.
 *
 * @property id идентификатор.
 * @property channelId идентификатор канала.
 * @property type тип сообщения.
 * @property pinned является ли сообщение закреплённым.
 * @property content содержимое сообщения.
 * @property mentionEveryone упоминает ли это сообщение всех.
 * @property messageReference ссылка на связанное сообщение.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @property mentions упомянутые пользователи.
 * @property referencedMessage источник ответа сообщения.
 * @property messageSnapshot пересланное сообщение.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class WsMessageDto(
    val id: UUID,
    val channelId: UUID,
    val type: String,
    val pinned: Boolean = false,
    val content: String? = null,
    val mentionEveryone: Boolean = false,
    val messageReference: MessageReference? = null,
    val createdDate: Instant,
    val updatedDate: Instant? = null,
) {
    lateinit var author: WsUserDto
    var mentions: List<WsUserDto>? = null
    var referencedMessage: MessageDto? = null
    var messageSnapshot: MessageSnapshotDto? = null
}