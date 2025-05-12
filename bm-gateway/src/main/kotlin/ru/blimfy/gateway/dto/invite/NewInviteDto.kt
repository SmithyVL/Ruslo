package ru.blimfy.gateway.dto.invite

import java.util.UUID
import ru.blimfy.server.db.entity.Invite

/**
 * DTO с информацией о новом приглашении на сервер.
 *
 * @property authorMemberId идентификатор участника сервера, создающего приглашение.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class NewInviteDto(val authorMemberId: UUID, val serverId: UUID)

/**
 * Возвращает сущность приглашения на сервер из DTO представления нового приглашения на сервер.
 */
fun NewInviteDto.toEntity() = Invite(authorMemberId = authorMemberId, serverId = serverId)
