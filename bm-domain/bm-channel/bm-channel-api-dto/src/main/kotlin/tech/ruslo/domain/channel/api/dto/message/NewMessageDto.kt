package tech.ruslo.domain.channel.api.dto.message

import java.util.UUID

/**
 * DTO с информацией о новом сообщении.
 *
 * @property type тип.
 * @property content содержимое.
 * @property messageReference ссылка на сообщение.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewMessageDto(
    val type: String,
    val content: String? = null,
    val messageReference: MessageReferenceDto? = null,
)

/**
 * DTO со ссылкой на сообщение.
 *
 * @property type тип ссылки.
 * @property messageId идентификатор сообщения.
 * @property channelId идентификатор канала.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessageReferenceDto(
    val type: String,
    val messageId: UUID,
    val channelId: UUID,
    val serverId: UUID? = null,
)