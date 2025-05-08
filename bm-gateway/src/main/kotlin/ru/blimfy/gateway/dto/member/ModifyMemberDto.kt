package ru.blimfy.gateway.dto.member

import java.time.Instant
import java.util.UUID
import ru.blimfy.server.db.entity.Member

/**
 * DTO с информацией о новом нике участника сервера.
 *
 * @property id идентификатор.
 * @property userId идентификатор пользователя.
 * @property nick ник участника сервера.
 * @property createdDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyMemberDto(val id: UUID, val userId: UUID, val nick: String? = null, val createdDate: Instant)

/**
 * Возвращает сущность участника сервера из DTO с информацией об обновлении участника сервера.
 */
fun ModifyMemberDto.toEntity(serverId: UUID) = Member(serverId, userId).apply {
    id = this@toEntity.id
    joiningDate = this@toEntity.createdDate
    nick = this@toEntity.nick
}