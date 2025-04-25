package ru.blimfy.persistence.entity

import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.InviteDto
import ru.blimfy.persistence.entity.base.BaseEntity

/**
 * Сущность, которая хранит в себе информацию о приглашении на сервер.
 *
 * @property authorId идентификатор автора приглашения.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Invite(val authorId: UUID, val serverId: UUID) : BaseEntity()

/**
 * Возвращает DTO представления сущности приглашения на сервер.
 */
fun Invite.toDto() = InviteDto(id, serverId, authorId)

/**
 * Возвращает сущность приглашения на сервер из DTO.
 */
fun InviteDto.toEntity() = Invite(authorId = authorId, serverId = serverId).apply {
    this@toEntity.id?.let { id = it }
}