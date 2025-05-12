package ru.blimfy.gateway.dto.member

import java.time.Instant
import java.util.UUID
import ru.blimfy.server.db.entity.Member

/**
 * DTO с информацией о новом нике участника сервера.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property userId идентификатор пользователя.
 * @property nick ник участника сервера.
 * @property joiningDate дата создания.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class ModifyMemberDto(
    val id: UUID,
    val serverId: UUID,
    val userId: UUID,
    val nick: String? = null,
    val joiningDate: Instant,
)

/**
 * Возвращает сущность участника сервера из DTO с информацией об обновлении участника сервера.
 */
fun ModifyMemberDto.toEntity() = Member(serverId, userId).apply {
    id = this@toEntity.id
    this.createdDate = this@toEntity.joiningDate
    nick = this@toEntity.nick
}