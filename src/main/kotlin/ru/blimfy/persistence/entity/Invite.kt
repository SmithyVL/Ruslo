package ru.blimfy.persistence.entity

import java.time.Instant
import java.util.UUID
import org.springframework.data.relational.core.mapping.Table
import ru.blimfy.common.dto.InviteDto
import ru.blimfy.persistence.entity.base.WithBaseData

/**
 * Сущность, которая хранит в себе информацию о приглашении на сервер.
 *
 * @property authorId идентификатор автора приглашения.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Table
data class Invite(val authorId: UUID, val serverId: UUID) : WithBaseData {
    override lateinit var id: UUID
    override lateinit var createdDate: Instant
    override var updatedDate: Instant? = null
    override fun isNew() = !::id.isInitialized
}

/**
 * Возвращает DTO представления сущности приглашения на сервер.
 */
fun Invite.toDto() = InviteDto(id, serverId, authorId, createdDate, updatedDate)

/**
 * Возвращает сущность приглашения на сервер из DTO.
 */
fun InviteDto.toEntity() = Invite(authorId, serverId).apply {
    this@toEntity.id?.let { id = it }
    this@toEntity.createdDate?.let { createdDate = it }
    this@toEntity.updatedDate?.let { updatedDate = it }
}