package ru.blimfy.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID

/**
 * DTO с информацией о сообщении канала.
 *
 * @property id идентификатор.
 * @property authorId идентификатор автора сообщения.
 * @property channelId идентификатор канала.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @property createdDate дата создания в UTC.
 * @property updatedDate дата обновления в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class TextMessageDto(
    val id: UUID? = null,
    val authorId: UUID,
    val channelId: UUID,
    val content: String,
    val fileUrl: String? = null,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val createdDate: Instant? = null,

    @param:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", timezone = "UTC")
    val updatedDate: Instant? = null,
)
