package ru.blimfy.gateway.integration.websockets.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.Instant
import java.time.Instant.now
import java.util.UUID
import ru.blimfy.gateway.integration.websockets.extra.MemberInfoDto

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
@JsonInclude(NON_NULL)
data class TypingStartDto(val channelId: UUID, val userId: UUID, val timestamp: Instant = now()) {
    var member: MemberInfoDto? = null
}