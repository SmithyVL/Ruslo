package ru.blimfy.gateway.dto.message.direct

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.common.enumeration.DirectMessageTypes
import ru.blimfy.direct.db.entity.DirectMessage
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_TIMEZONE
import ru.blimfy.gateway.dto.user.UserDto

/**
 * DTO с информацией о личном сообщении.
 *
 * @property id идентификатор.
 * @property conservationId идентификатор личного диалога.
 * @property content содержимое сообщения.
 * @property type тип сообщения.
 * @property pinned является ли сообщение закреплённым.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @property author информация об авторе.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class DirectMessageDto(
    val id: UUID,
    val conservationId: UUID,
    val content: String,
    val type: DirectMessageTypes,
    val pinned: Boolean,
    val fileUrl: String? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val createdDate: Instant,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = INSTANT_TIMEZONE)
    val updatedDate: Instant? = null,
) {
    lateinit var author: UserDto
}

/**
 * Возвращает сущность сообщения личного диалога из DTO представления сообщения личного диалога для [authorId].
 */
fun DirectMessageDto.toEntity(authorId: UUID) =
    DirectMessage(conservationId, authorId, content).apply {
        id = this@toEntity.id
        type = this@toEntity.type
        pinned = this@toEntity.pinned
        fileUrl = this@toEntity.fileUrl
        createdDate = this@toEntity.createdDate
        updatedDate = this@toEntity.updatedDate
    }

/**
 * Возвращает DTO представления сущности сообщения личного диалога.
 */
fun DirectMessage.toDto() =
    DirectMessageDto(id, conservationId, content, type, pinned, fileUrl, createdDate, updatedDate)