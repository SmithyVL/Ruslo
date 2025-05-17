package ru.blimfy.gateway.dto.server.message

import java.util.UUID
import ru.blimfy.server.db.entity.TextMessage

/**
 * DTO с информацией о новом текстовом сообщении канала.
 *
 * @property channelId идентификатор канала.
 * @property content содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewTextMessageDto(val channelId: UUID, val content: String? = null)

/**
 * Возвращает сущность текстового сообщения канала сервера из DTO представления.
 */
fun NewTextMessageDto.toEntity(authorId: UUID) = TextMessage(channelId, authorId, content)