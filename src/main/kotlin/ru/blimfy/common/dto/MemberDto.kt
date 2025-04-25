package ru.blimfy.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.Application.Companion.INSTANT_FORMAT

/**
 * DTO с информацией об участнике сервера.
 *
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property id идентификатор.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MemberDto(
    val serverId: UUID,
    val userId: UUID,
    val id: UUID? = null,
    @JsonFormat(pattern = INSTANT_FORMAT, timezone = "UTC") val createdDate: Instant? = null,
)