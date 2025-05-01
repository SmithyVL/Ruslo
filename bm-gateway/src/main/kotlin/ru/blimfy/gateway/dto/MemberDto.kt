package ru.blimfy.gateway.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.util.UUID
import ru.blimfy.gateway.config.WebConfig.Companion.INSTANT_FORMAT
import ru.blimfy.server.db.entity.Member

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

/**
 * Возвращает DTO представление сущности участника сервера.
 */
fun Member.toDto() = MemberDto(serverId, userId, id, createdDate)

/**
 * Возвращает сущность участника сервера из DTO.
 */
fun MemberDto.toEntity() = Member(serverId, userId).apply {
    this@toEntity.id?.let { id = it }
    this@toEntity.createdDate?.let { createdDate = it }
}