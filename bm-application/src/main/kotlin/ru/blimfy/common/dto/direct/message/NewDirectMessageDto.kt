package ru.blimfy.common.dto.direct.message

import java.util.UUID

/**
 * DTO с информацией о новом личном сообщении.
 *
 * @property conservationId идентификатор личного диалога.
 * @property content содержимое сообщения.
 * @property fileUrl ссылка на файл, прикреплённый к сообщению.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewDirectMessageDto(val conservationId: UUID, val content: String, val fileUrl: String? = null)