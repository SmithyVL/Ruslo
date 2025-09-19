package tech.ruslo.gateway.dto.websockets

import java.time.Instant
import java.time.Instant.now
import java.util.UUID

/**
 * DTO с полезной нагрузкой для события, вызванного началом набора текста пользователем в канале.
 *
 * @property channelId идентификатор канала.
 * @property userId идентификатор пользователя.
 * @property timestamp дата начала набора текста.
 * @property member информация об участнике сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class TypingStartDto(val channelId: UUID, val userId: UUID, val timestamp: Instant = now()) {
    var member: PartialMemberDto? = null
}