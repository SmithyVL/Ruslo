package ru.blimfy.gateway.dto.invite

import java.util.UUID
import ru.blimfy.server.db.entity.Invite

/**
 * DTO с информацией о приглашении на сервер.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property authorMemberId идентификатор участника сервера, создавшего приглашение.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class InviteDto(val id: UUID? = null, val serverId: UUID, val authorMemberId: UUID)

/**
 * Возвращает сущность приглашения на сервер из DTO.
 */
fun InviteDto.toEntity() = Invite(authorMemberId = authorMemberId, serverId = serverId).apply {
    this@toEntity.id?.let { id = it }
}

/**
 * Возвращает DTO представления сущности приглашения на сервер.
 */
fun Invite.toDto() = InviteDto(id, serverId, authorMemberId)
