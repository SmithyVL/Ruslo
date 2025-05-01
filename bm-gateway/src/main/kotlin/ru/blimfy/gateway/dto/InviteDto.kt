package ru.blimfy.gateway.dto

import java.util.UUID
import ru.blimfy.server.db.entity.Invite

/**
 * DTO с информацией о приглашении на сервер.
 *
 * @property id идентификатор.
 * @property serverId идентификатор сервера.
 * @property authorId автор приглашения.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class InviteDto(val id: UUID? = null, val serverId: UUID, val authorId: UUID)

/**
 * Возвращает сущность приглашения на сервер из DTO.
 */
fun InviteDto.toEntity() = Invite(authorId = authorId, serverId = serverId).apply {
    this@toEntity.id?.let { id = it }
}

/**
 * Возвращает DTO представления сущности приглашения на сервер.
 */
fun Invite.toDto() = InviteDto(id, serverId, authorId)
