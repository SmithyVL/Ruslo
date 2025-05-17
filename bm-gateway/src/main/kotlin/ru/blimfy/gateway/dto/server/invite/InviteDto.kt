package ru.blimfy.gateway.dto.server.invite

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
data class InviteDto(val id: UUID, val serverId: UUID, val authorMemberId: UUID)

/**
 * Возвращает DTO представление сущности приглашения на сервер.
 */
fun Invite.toDto() = InviteDto(id, serverId, authorMemberId)
