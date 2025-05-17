package ru.blimfy.gateway.dto.server.message

import java.util.UUID

/**
 * DTO с новым содержим для текстового сообщения.
 *
 * @property channelId идентификатор канала.
 * @property content содержимое сообщения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class TextMessageContentDto(val channelId: UUID, val content: String)