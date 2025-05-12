package ru.blimfy.gateway.dto.message.text

import java.util.UUID
import ru.blimfy.server.db.entity.TextMessage

/**
 * DTO с информацией о новом текстовом сообщении канала.
 *
 * @property channelId идентификатор текстового канала.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewTextMessageDto(val channelId: UUID, val content: String, val fileUrl: String? = null)

/**
 * Возвращает сущность текстового сообщения канала сервера из DTO представления текстового сообщения канала сервера.
 */
fun NewTextMessageDto.toEntity(authorId: UUID) =
    TextMessage(channelId, authorId, content).apply { fileUrl = this@toEntity.fileUrl }