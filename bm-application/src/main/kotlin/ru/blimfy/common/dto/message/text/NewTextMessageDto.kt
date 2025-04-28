package ru.blimfy.common.dto.message.text

import java.util.UUID

/**
 * DTO с информацией о новом текстовом сообщении канала.
 *
 * @property channelId идентификатор канала.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewTextMessageDto(val channelId: UUID, val content: String, val fileUrl: String? = null)